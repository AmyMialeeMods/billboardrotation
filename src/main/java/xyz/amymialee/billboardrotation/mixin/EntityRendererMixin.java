package xyz.amymialee.billboardrotation.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.billboardrotation.util.Should;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Inject(method = "updateRenderState", at = @At(value = "TAIL"))
    private void billboardrotation$should(@NotNull T entity, S state, float tickDelta, CallbackInfo ci) {
        ((Should) state).billboardrotation$setShould(((Should) entity).billboardrotation$should());
        ((Should) entity).billboardrotation$setShould(false);
        entity.prevPitch = entity.getPitch();
        entity.prevYaw = entity.getYaw();
    }
}