package xyz.amymialee.billboardrotation.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.billboardrotation.BillboardRotation;
import xyz.amymialee.billboardrotation.util.Should;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements Should {
    @Unique
    private boolean billboardrotation$should = true;

    @Override
    public boolean billboardrotation$should() {
        return this.billboardrotation$should;
    }

    @Override
    public void billboardrotation$setShould(boolean should) {
        this.billboardrotation$should = should;
    }
}
