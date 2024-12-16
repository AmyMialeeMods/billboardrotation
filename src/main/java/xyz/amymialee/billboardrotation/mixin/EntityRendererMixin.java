package xyz.amymialee.billboardrotation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.state.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.billboardrotation.BillboardRotation;
import xyz.amymialee.billboardrotation.util.SingleTicker;
import xyz.amymialee.billboardrotation.util.StateHolder;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Shadow public abstract S createRenderState();

    @WrapOperation(method = "getAndUpdateRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;updateRenderState(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/entity/state/EntityRenderState;F)V"))
    private void billboardrotation$holding(EntityRenderer instance, T entity, S state, float tickDelta, Operation<Void> original) {
        if (BillboardRotation.ANIMATION_DELAY.get() == 0) {
            original.call(instance, entity, state, tickDelta);
            return;
        }
        if (entity instanceof StateHolder holder) {
            if (holder.billboardrotation$getState() == null) holder.billboardrotation$setState(this.createRenderState());
            if (BillboardRotation.shouldSetAngles(entity) && ((SingleTicker) holder.billboardrotation$getState()).billboardrotation$shouldUpdate()) {
                ((SingleTicker) holder.billboardrotation$getState()).billboardrotation$setShouldUpdate(false);
                original.call(instance, entity, holder.billboardrotation$getState(), tickDelta);
            } else if (entity instanceof LivingEntity livingEntity && holder.billboardrotation$getState() instanceof LivingEntityRenderState renderState) {
                var yaw = MathHelper.lerpAngleDegrees(tickDelta, livingEntity.prevHeadYaw, livingEntity.headYaw);
                renderState.bodyYaw = BillboardRotation.getBodyRotation(new Vec3d(entity.prevX, entity.prevY, entity.prevZ), entity.getPos(), clampBodyYaw(livingEntity, yaw, tickDelta));
                renderState.yawDegrees = BillboardRotation.getHeadRotation(MathHelper.wrapDegrees(yaw - renderState.bodyYaw));
            }
            if (entity.hasPassengerDeep(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().gameRenderer.getCamera().isThirdPerson() && holder.billboardrotation$getState() instanceof BoatEntityRenderState renderState) {
                renderState.yaw = entity.getLerpedYaw(tickDelta);
            }
        }
    }

    @Inject(method = "getAndUpdateRenderState", at = @At(value = "TAIL"), cancellable = true)
    private void billboardrotation$holding(@NotNull T entity, float tickDelta, @NotNull CallbackInfoReturnable<S> cir) {
        if (BillboardRotation.ANIMATION_DELAY.get() == 0) return;
        cir.setReturnValue(entity instanceof StateHolder holder ? (S) holder.billboardrotation$getState() : cir.getReturnValue());
    }

    @Unique
    private static float clampBodyYaw(@NotNull LivingEntity entity, float degrees, float tickDelta) {
        if (!(entity.getVehicle() instanceof LivingEntity livingEntity)) return MathHelper.lerpAngleDegrees(tickDelta, entity.prevBodyYaw, entity.bodyYaw);
        var body = MathHelper.lerpAngleDegrees(tickDelta, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
        var clamped = MathHelper.clamp(MathHelper.wrapDegrees(degrees - body), -85.0F, 85.0F);
        body = degrees - clamped;
        if (Math.abs(clamped) > 50.0F) body += clamped * 0.2F;
        return body;
    }
}