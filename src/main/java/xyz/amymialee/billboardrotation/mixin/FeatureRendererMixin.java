package xyz.amymialee.billboardrotation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.billboardrotation.BillboardRotation;

@Mixin(FeatureRenderer.class)
public class FeatureRendererMixin {
//    @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/model/EntityModel;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//    private static <T extends EntityRenderState> void billboardrotation$interval(EntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//        if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//    }
//
//    @Mixin(BreezeWindFeatureRenderer.class)
//    private static class BreezeWindFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BreezeEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BreezeEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/BreezeEntityRenderState;)V"))
//        private void billboardrotation$interval(BreezeEntityModel instance, @NotNull BreezeEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(CapeFeatureRenderer.class)
//    private static class CapeFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;)V"))
//        private <T extends BipedEntityRenderState> void billboardrotation$interval(BipedEntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(Deadmau5FeatureRenderer.class)
//    private static class Deadmau5FeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;)V"))
//        private <T extends BipedEntityRenderState> void billboardrotation$interval(BipedEntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(ElytraFeatureRenderer.class)
//    private static class ElytraFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ElytraEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;)V"))
//        private void billboardrotation$interval(ElytraEntityModel instance, @NotNull BipedEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(EnergySwirlOverlayFeatureRenderer.class)
//    private static class EnergySwirlOverlayFeatureRendererMixin {
//        @WrapOperation(method = "render",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private <T extends EntityRenderState> void billboardrotation$interval(EntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(HorseArmorFeatureRenderer.class)
//    private static class HorseArmorFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/HorseEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/HorseEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/LivingHorseEntityRenderState;)V"))
//        private void billboardrotation$interval(HorseEntityModel instance, @NotNull LivingHorseEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(LlamaDecorFeatureRenderer.class)
//    private static class LlamaDecorFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/LlamaEntityRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/registry/RegistryKey;I)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/LlamaEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/LlamaEntityRenderState;)V"))
//        private void billboardrotation$interval(LlamaEntityModel instance, @NotNull LlamaEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(SaddleFeatureRenderer.class)
//    private static class SaddleFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private <T extends EntityRenderState> void billboardrotation$interval(EntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(SheepWoolFeatureRenderer.class)
//    private static class SheepWoolFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/SheepEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private <T extends EntityRenderState> void billboardrotation$interval(EntityModel<T> instance, @NotNull T state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(ShoulderParrotFeatureRenderer.class)
//    private static class ShoulderParrotFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/entity/passive/ParrotEntity$Variant;FFZ)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ParrotEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/ParrotEntityRenderState;)V"))
//        private void billboardrotation$interval(ParrotEntityModel instance, @NotNull ParrotEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(SlimeOverlayFeatureRenderer.class)
//    private static class SlimeOverlayFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/SlimeEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/SlimeEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V"))
//        private void billboardrotation$interval(SlimeEntityModel instance, @NotNull EntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(TridentRiptideFeatureRenderer.class)
//    private static class TridentRiptideFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentRiptideEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V"))
//        private void billboardrotation$interval(TridentRiptideEntityModel instance, @NotNull PlayerEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
//
//    @Mixin(WolfArmorFeatureRenderer.class)
//    private static class WolfArmorFeatureRendererMixin {
//        @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/WolfEntityRenderState;FF)V",
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/WolfEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/WolfEntityRenderState;)V"))
//        private void billboardrotation$interval(WolfEntityModel instance, @NotNull WolfEntityRenderState state, Operation<Void> original) {
//            if (BillboardRotation.shouldSetAngles(instance, state.age)) original.call(instance, state);
//        }
//    }
}