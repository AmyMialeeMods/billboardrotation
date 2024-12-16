package xyz.amymialee.billboardrotation.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.amymialee.billboardrotation.util.SingleTicker;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements SingleTicker {
    @Unique private boolean shouldUpdate = true;

    @Override
    public boolean billboardrotation$shouldUpdate() {
        return this.shouldUpdate;
    }

    @Override
    public void billboardrotation$setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }
}