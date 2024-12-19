package xyz.amymialee.billboardrotation.util;

import net.minecraft.client.render.entity.state.EntityRenderState;

public interface StateHolder {
    EntityRenderState billboardrotation$getState();
    void billboardrotation$setState(EntityRenderState state);
}