package xyz.amymialee.billboardrotation;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.mialib.mvalues.MValue;
import xyz.amymialee.mialib.mvalues.MValueCategory;
import xyz.amymialee.mialib.mvalues.MValueType;

public class BillboardRotation implements ClientModInitializer {
    public static final String MOD_ID = "billboardrotation";

    public static final MValueCategory BILLBOARD_ROTATION = new MValueCategory(id(MOD_ID), Items.COMPASS, Identifier.ofVanilla("textures/block/deepslate_tiles.png"), 16, 16);
    public static final MValue<Integer> HEAD_ROTATION_INTERVAL = MValue.of(id("head_rotation_interval"), angleSplitter(3, 0, 4)).category(BILLBOARD_ROTATION).clientSide().item(Items.CREEPER_HEAD).build();
    public static final MValue<Integer> BODY_ROTATION_INTERVAL = MValue.of(id("body_rotation_interval"), angleSplitter(2, 0, 4)).category(BILLBOARD_ROTATION).clientSide().item(Items.COMPASS).build();
    public static final MValue<Integer> ANIMATION_DELAY = MValue.of(id("animation_delay"), MValue.INTEGER.of(4, 0, 20)).category(BILLBOARD_ROTATION).clientSide().item(Items.CLOCK).build();
    public static final MValue<Boolean> ROTATE_VERTICALLY = MValue.of(id("rotate_vertically"), MValue.BOOLEAN_TRUE).category(BILLBOARD_ROTATION).clientSide().item(Items.ENDER_EYE).build();

    @Override
    public void onInitializeClient() {}

    public static @NotNull MValueType.MValueMinMax<Integer> angleSplitter(Integer defaultValue, Integer min, Integer max) {
        return new MValueType.MValueInteger(defaultValue, min, max) {
            @Override
            public @NotNull String getValueAsString(@NotNull MValue<Integer> value) {
                return "%.2f".formatted(360.0 / Math.pow(2, value.get()));
            }
        };
    }

    public static boolean shouldSetAngles(Entity entity) {
        if (ANIMATION_DELAY.get() == 0) return true;
        return entity.age % ANIMATION_DELAY.get() == 0;
    }

    public static float getBodyRotation(Vec3d lastPos, Vec3d pos, float original) {
        var interval = (float) (360.0 / Math.pow(2, BODY_ROTATION_INTERVAL.get()));
        if (BODY_ROTATION_INTERVAL.get() <= 0) return original;
        var client = MinecraftClient.getInstance();
        var player = client.gameRenderer.getCamera();
        if (player == null) return original;
        var delta = client.getRenderTickCounter().getTickDelta(true);
        var look = player.getPos().subtract(lastPos.lerp(pos, delta)).multiply(1, 0, 1).normalize();
        var angle = (float) Math.toDegrees(Math.atan2(look.getZ(), look.getX()));
        var endAngle = ((720 + angle + interval / 2) % interval - interval / 2);
        var value = original;
        value /= interval;
        value = Math.round(value) * interval;
        return MathHelper.wrapDegrees(endAngle + value);
    }

    public static float getHeadRotation(float original) {
        var interval = (float) (360f / Math.pow(2, HEAD_ROTATION_INTERVAL.get()));
        if (HEAD_ROTATION_INTERVAL.get() <= 0) return original;
        var client = MinecraftClient.getInstance();
        var player = client.gameRenderer.getCamera();
        if (player == null) return original;
        var value = original;
        value /= interval;
        value = Math.round(value) * interval;
        return MathHelper.wrapDegrees(value);
    }

    public static void modifyVertex(float x, float y, float z, int color, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, @NotNull Operation<Void> original) {
        var interval = (float) (360f / Math.pow(2, BODY_ROTATION_INTERVAL.get()));
        if (BODY_ROTATION_INTERVAL.get() > 0) {
            var normal = new Vec3d(normalX, normalY, normalZ);
            var yaw = (float) Math.toDegrees(Math.atan2(normal.getZ(), normal.getX()));
            yaw /= interval;
            yaw = Math.round(yaw) * interval;
            normalX = (float) Math.cos(Math.toRadians(yaw));
            normalZ = (float) Math.sin(Math.toRadians(yaw));
            var normalLook = new Vec3d(normalX, normalY, normalZ).normalize();
            normalX = (float) normalLook.getX();
            normalY = (float) normalLook.getY();
            normalZ = (float) normalLook.getZ();
        }
        original.call(x, y, z, color, u, v, overlay, light, normalX, normalY, normalZ);
    }

    public static <S extends EntityRenderState, E extends Entity> void verticalRotation(EntityRenderer<E, S> instance, S state, @NotNull MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original, E entity) {
        matrices.push();
        var client = MinecraftClient.getInstance();
        var player = client.player;
        var camera = client.gameRenderer.getCamera();
        if (ROTATE_VERTICALLY.get() && player != null && camera != null && !entity.hasPassengerDeep(player)) {
            matrices.translate(0, entity.getHeight() / 2, 0);
            var delta = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
            var look = camera.getPos().subtract(new Vec3d(entity.prevX, entity.prevY, entity.prevZ).lerp(entity.getPos(), delta)).multiply(1, 0, 1).normalize();
            var angle = (float) Math.toDegrees(Math.atan2(look.getX(), look.getZ()));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
            var height = 0f;
            if (camera.getFocusedEntity() != null) height = camera.getFocusedEntity().getStandingEyeHeight();
            var entityPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ).lerp(new Vec3d(entity.getX(), entity.getY(), entity.getZ()), delta).add(0, height, 0);
            var yAngle = entityPos.subtract(camera.getPos()).normalize().y * 60;
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees((float) -yAngle));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-angle));
            matrices.translate(0, -entity.getHeight() / 2, 0);
        }
        original.call(instance, state, matrices, vertexConsumers, light);
        matrices.pop();
    }

    public static @NotNull Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}