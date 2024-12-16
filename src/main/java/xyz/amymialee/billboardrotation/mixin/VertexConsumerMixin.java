package xyz.amymialee.billboardrotation.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumers;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import xyz.amymialee.billboardrotation.BillboardRotation;

@Mixin(BufferBuilder.class)
public class VertexConsumerMixin {
    @WrapMethod(method = "vertex(FFFIFFIIFFF)V")
    private void billboardrotation$wrapVertex(float x, float y, float z, int color, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, @NotNull Operation<Void> original) {
        BillboardRotation.modifyVertex(x, y, z, color, u, v, overlay, light, normalX, normalY, normalZ, original);
    }
}