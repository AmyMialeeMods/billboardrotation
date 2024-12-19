package xyz.amymialee.billboardrotation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.billboardrotation.BillboardRotation;
import xyz.amymialee.billboardrotation.util.StateHolder;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Shadow public abstract S createRenderState();

    @WrapOperation(method = "getAndUpdateRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;updateRenderState(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/entity/state/EntityRenderState;F)V"))
    private void billboardrotation$holding(EntityRenderer<T, S> instance, T entity, S state, float tickDelta, Operation<Void> original) {
        if (entity instanceof StateHolder holder && holder.billboardrotation$getState() == null) holder.billboardrotation$setState(this.createRenderState());
        BillboardRotation.getAndUpdateRenderState(instance, entity, state, tickDelta, original);
    }

    @Inject(method = "getAndUpdateRenderState", at = @At(value = "TAIL"), cancellable = true)
    private void billboardrotation$holding(@NotNull T entity, float tickDelta, @NotNull CallbackInfoReturnable<S> cir) {
        BillboardRotation.stateHolderCheck(entity, cir);
    }
}