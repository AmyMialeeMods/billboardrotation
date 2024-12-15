package xyz.amymialee.billboardrotation.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.amymialee.billboardrotation.util.StateHolder;

@Mixin(Entity.class)
public class EntityMixin implements StateHolder {
    @Unique private EntityRenderState billboardrotation$state;

    @Override
    public EntityRenderState billboardrotation$getState() {
        return this.billboardrotation$state;
    }

    @Override
    public void billboardrotation$setState(EntityRenderState state) {
        this.billboardrotation$state = state;
    }
}