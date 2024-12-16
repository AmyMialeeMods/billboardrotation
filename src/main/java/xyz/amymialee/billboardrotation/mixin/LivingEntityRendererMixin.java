package xyz.amymialee.billboardrotation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.billboardrotation.BillboardRotation;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @WrapOperation(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;clampBodyYaw(Lnet/minecraft/entity/LivingEntity;FF)F"))
    private float billboardrotation$clamping(@NotNull LivingEntity entity, float degrees, float tickDelta, @NotNull Operation<Float> original) {
        return BillboardRotation.getBodyRotation(new Vec3d(entity.prevX, entity.prevY, entity.prevZ), entity.getPos(), original.call(entity, degrees, tickDelta));
    }

    @WrapOperation(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;wrapDegrees(F)F"))
    private float billboardrotation$clamping(float degrees, @NotNull Operation<Float> original, T entity) {
        return BillboardRotation.getHeadRotation(original.call(degrees));
    }

    @WrapOperation(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getLerpedPitch(F)F"))
    private float billboardrotation$pitchless(LivingEntity instance, float pitch, Operation<Float> original) {
        if (BillboardRotation.ROTATE_VERTICALLY.get()) return 0;
        return original.call(instance, pitch);
    }
}