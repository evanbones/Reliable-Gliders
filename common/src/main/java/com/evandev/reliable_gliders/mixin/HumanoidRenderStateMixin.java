package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GliderStateAccess;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements GliderStateAccess {
    @Unique
    private boolean reliableGliders$isGliding;

    @Override
    public boolean reliableGliders$isGliding() {
        return this.reliableGliders$isGliding;
    }

    @Override
    public void reliableGliders$setGliding(boolean gliding) {
        this.reliableGliders$isGliding = gliding;
    }
}