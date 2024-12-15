package xyz.amymialee.billboardrotation;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
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

public class BillboardRotation implements ClientModInitializer {
    public static final String MOD_ID = "billboardrotation";

    public static final MValueCategory BILLBOARD_ROTATION = new MValueCategory(id(MOD_ID), Items.COMPASS, Identifier.ofVanilla("textures/block/deepslate_tiles.png"), 16, 16);
    public static final MValue<Integer> HEAD_ROTATION_INTERVAL = MValue.of(id("head_rotation_interval"), MValue.INTEGER.of(45, 0, 360)).category(BILLBOARD_ROTATION).clientSide().item(Items.CREEPER_HEAD).build();
    public static final MValue<Integer> BODY_ROTATION_INTERVAL = MValue.of(id("body_rotation_interval"), MValue.INTEGER.of(45, 0, 360)).category(BILLBOARD_ROTATION).clientSide().item(Items.COMPASS).build();
    public static final MValue<Boolean> ADJUST_TO_PLAYER_ROTATION = MValue.of(id("adjust_to_player_rotation"), MValue.BOOLEAN_TRUE).category(BILLBOARD_ROTATION).clientSide().item(Items.PLAYER_HEAD).build();
    public static final MValue<Integer> ANIMATION_DELAY = MValue.of(id("animation_delay"), MValue.INTEGER.of(1, 1, 60)).category(BILLBOARD_ROTATION).clientSide().item(Items.CLOCK).build();
    public static final MValue<Boolean> ROTATE_VERTICALLY = MValue.of(id("rotate_vertically"), MValue.BOOLEAN_TRUE).category(BILLBOARD_ROTATION).clientSide().item(Items.ENDER_EYE).build();

    public static boolean shouldSetAngles(int age) {
        return age % ANIMATION_DELAY.get() == 0;
    }

    @Override
    public void onInitializeClient() {}

    public static float getBodyRotation(Vec3d lastPos, Vec3d pos, float original, float interval) {
        var client = MinecraftClient.getInstance();
        var player = client.gameRenderer.getCamera();
        if (player == null || interval <= 0) return original;
        var delta = client.getRenderTickCounter().getTickDelta(true);
        var last = player.getPos();//new Vec3d(player.prevX, player.prevY, player.prevZ).lerp(player.getPos(), delta);
        var look = last.subtract(lastPos.lerp(pos, delta)).multiply(1, 0, 1).normalize();
        var angle = (float) Math.toDegrees(Math.atan2(look.getZ(), look.getX()));
        var endAngle = ((360 + angle + interval / 2) % interval - interval / 2) - 360;
        var value = original;
        value /= interval;
        value = Math.round(value) * interval;
        return MathHelper.wrapDegrees(endAngle + value);
    }

    public static float getHeadRotation(float original, float interval) {
        var client = MinecraftClient.getInstance();
        var player = client.gameRenderer.getCamera();
        if (player == null || interval <= 0) return original;
        var value = original;
        value /= interval;
        value = Math.round(value) * interval;
        return MathHelper.wrapDegrees(value);
    }

    public static void modifyVertex(float x, float y, float z, int color, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, @NotNull Operation<Void> original) {
        var interval = BODY_ROTATION_INTERVAL.get();
        if (interval > 0) {
            var normal = new Vec3d(normalX, normalY, normalZ);
            var yaw = (float) Math.toDegrees(Math.atan2(normal.getZ(), normal.getX()));
            yaw /= interval;
            yaw = Math.round(yaw) * interval;
            normalX = (float) Math.cos(Math.toRadians(yaw));
            normalZ = (float) Math.sin(Math.toRadians(yaw));
            var normalLook = new Vec3d(normalX, 0, normalZ).normalize();
            normalX = (float) normalLook.getX();
            normalY = (float) normalLook.getY();
            normalZ = (float) normalLook.getZ();
        }
        original.call(x, y, z, color, u, v, overlay, light, normalX, normalY, normalZ);
    }

    public static <S extends EntityRenderState, E extends Entity> void verticalRotation(EntityRenderer<E, S> instance, S state, @NotNull MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original, E entity) {
        matrices.push();
        var player = MinecraftClient.getInstance().gameRenderer.getCamera();
        if (player != null) {
            matrices.translate(0, entity.getHeight() / 2, 0);
            var delta = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
            var last = player.getPos();//(new Vec3d(player.prevX, player.prevY, player.prevZ).lerp(player.getPos(), delta));
            var look = last.subtract(new Vec3d(entity.prevX, entity.prevY, entity.prevZ).lerp(entity.getPos(), delta)).multiply(1, 0, 1).normalize();
            var angle = (float) Math.toDegrees(Math.atan2(look.getX(), look.getZ()));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
            var playerPos = player.getPos();//new Vec3d(player.prevX, player.prevY + player.getStandingEyeHeight(), player.prevZ).lerp(new Vec3d(player.getX(), player.getEyeY(), player.getZ()), delta);
            var entityPos = new Vec3d(entity.prevX, entity.prevY + entity.getHeight() / 2, entity.prevZ).lerp(new Vec3d(entity.getX(), entity.getBodyY(.5), entity.getZ()), delta);
            var yAngle = entityPos.subtract(playerPos).normalize().y * 60;
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