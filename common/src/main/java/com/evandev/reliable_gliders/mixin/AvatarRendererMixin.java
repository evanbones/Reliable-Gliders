package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GliderStateAccess;
import com.evandev.reliable_gliders.api.GlidingState;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("RETURN"))
    private void reliableGliders$extractGliding(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Player player) {
            ((GliderStateAccess) state).reliableGliders$setGliding(GlidingState.isGliding(player));
        }
    }
}