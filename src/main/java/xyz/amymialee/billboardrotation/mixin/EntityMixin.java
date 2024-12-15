package xyz.amymialee.billboardrotation.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.billboardrotation.BillboardRotation;
import xyz.amymialee.billboardrotation.util.Should;

@Mixin(Entity.class)
public class EntityMixin implements Should {
    @Unique private boolean billboardrotation$should = true;

    @Shadow public int age;

    @Inject(method = "tick", at = @At("HEAD"))
    private void billboardrotation$update(CallbackInfo ci) {
        if (this.age % BillboardRotation.ANIMATION_DELAY.get() == 0) this.billboardrotation$setShould(true);
    }

    @Override
    public boolean billboardrotation$should() {
        return this.billboardrotation$should;
    }

    @Override
    public void billboardrotation$setShould(boolean should) {
        this.billboardrotation$should = should;
    }
}
