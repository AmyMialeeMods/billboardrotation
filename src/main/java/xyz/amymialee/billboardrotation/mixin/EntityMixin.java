package xyz.amymialee.billboardrotation.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.billboardrotation.util.SingleTicker;
import xyz.amymialee.billboardrotation.util.StateHolder;

@Mixin(Entity.class)
public class EntityMixin implements StateHolder {
    @Unique private EntityRenderState billboardrotation$state;

    @Inject(method = "tick", at = @At("HEAD"))
    private void billboardrotation$tickState(CallbackInfo ci) {
        if (this.billboardrotation$state != null) ((SingleTicker) this.billboardrotation$state).tick();
    }

    @Override
    public EntityRenderState billboardrotation$getState() {
        return this.billboardrotation$state;
    }

    @Override
    public void billboardrotation$setState(EntityRenderState state) {
        this.billboardrotation$state = state;
    }
}