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

//    @Mixin(AbstractBoatEntityRenderer.class)
//    private static class AbstractBoatEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/BoatEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private <T extends EntityRenderState> void billboardrotation$interval(EntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(AbstractMinecartEntityRenderer.class)
//    private static class AbstractMinecartEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/MinecartEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/MinecartEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private void billboardrotation$interval(MinecartEntityModel instance, @NotNull EntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(EndCrystalEntityRenderer.class)
//    private static class EndCrystalEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EndCrystalEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;)V"))
//        private void billboardrotation$interval(EndCrystalEntityModel instance, @NotNull EndCrystalEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(EnderDragonEntityRenderer.class)
//    private static class EnderDragonEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/EnderDragonEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/DragonEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EnderDragonEntityRenderState;)V"))
//        private void billboardrotation$interval(DragonEntityModel instance, @NotNull EnderDragonEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(EvokerFangsEntityRenderer.class)
//    private static class EvokerFangsEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/EvokerFangsEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EvokerFangsEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EvokerFangsEntityRenderState;)V"))
//        private void billboardrotation$interval(EvokerFangsEntityModel instance, @NotNull EvokerFangsEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(LeashKnotEntityRenderer.class)
//    private static class LeashKnotEntityRendererMixin {
//        @WrapOperation(method = "render",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/LeashKnotEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private void billboardrotation$interval(LeashKnotEntityModel instance, @NotNull EntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(LivingEntityRenderer.class)
//    private static class LivingEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private <T extends EntityRenderState> void billboardrotation$interval(EntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(LlamaSpitEntityRenderer.class)
//    private static class LlamaSpitEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/LlamaSpitEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/LlamaSpitEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private void billboardrotation$interval(LlamaSpitEntityModel instance, @NotNull EntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(ProjectileEntityRenderer.class)
//    private static class ProjectileEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/ProjectileEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ArrowEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/ProjectileEntityRenderState;)V"))
//        private void billboardrotation$interval(ArrowEntityModel instance, @NotNull ProjectileEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(ShulkerBulletEntityRenderer.class)
//    private static class ShulkerBulletEntityRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/ShulkerBulletEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ShulkerBulletEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/ShulkerBulletEntityRenderState;)V"))
//        private void billboardrotation$interval(ShulkerBulletEntityModel instance, @NotNull ShulkerBulletEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(WindChargeEntityRenderer.class)
//    private static class WindChargeEntityRendererMixin {
//        @WrapOperation(method = "render",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/WindChargeEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private void billboardrotation$interval(WindChargeEntityModel instance, @NotNull EntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
}