package xyz.amymialee.billboardrotation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.*;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.billboardrotation.BillboardRotation;
import xyz.amymialee.billboardrotation.util.Should;
import xyz.amymialee.billboardrotation.util.StateHolder;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Shadow public abstract S createRenderState();

    @WrapOperation(method = "getAndUpdateRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;updateRenderState(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/entity/state/EntityRenderState;F)V"))
    private void billboardrotation$holding(EntityRenderer instance, T entity, S state, float tickDelta, Operation<Void> original) {
        if (entity instanceof StateHolder holder) {
            if (holder.billboardrotation$getState() == null) holder.billboardrotation$setState(this.createRenderState());
            if (BillboardRotation.shouldSetAngles(entity.age)) original.call(instance, entity, holder.billboardrotation$getState(), tickDelta);
        }
    }

    @Inject(method = "getAndUpdateRenderState", at = @At(value = "TAIL"), cancellable = true)
    private void billboardrotation$holding(@NotNull T entity, float tickDelta, @NotNull CallbackInfoReturnable<S> ci) {
        ci.setReturnValue(entity instanceof StateHolder holder ? (S) holder.billboardrotation$getState() : ci.getReturnValue());
    }

    @Inject(method = "updateRenderState", at = @At(value = "TAIL"))
    private void billboardrotation$should(@NotNull T entity, S state, float tickDelta, CallbackInfo ci) {
        entity.prevPitch = entity.getPitch();
        entity.prevYaw = entity.getYaw();
    }
}