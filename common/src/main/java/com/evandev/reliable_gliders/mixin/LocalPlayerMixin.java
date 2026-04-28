package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.client.GliderSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Unique
    private boolean reliableGliders$playGliderSound = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void reliableGliders$tickGliderSound(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        if (GlidingState.isGliding(player) && !this.reliableGliders$playGliderSound) {
            this.reliableGliders$playGliderSound = true;
            Minecraft.getInstance().getSoundManager().play(new GliderSoundInstance(player));
        } else if (!GlidingState.isGliding(player) && this.reliableGliders$playGliderSound) {
            this.reliableGliders$playGliderSound = false;
        }
    }
}