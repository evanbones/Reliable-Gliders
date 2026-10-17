package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.api.GlidingState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class GliderSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayer player;
    private float fade;

    public GliderSoundInstance(LocalPlayer player) {
        super(SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.1F;
        this.fade = 0.0F;
    }

    @Override
    public void tick() {
        boolean isGliding = !this.player.isRemoved() && GlidingState.isGliding(this.player);

        if (isGliding && this.fade < 1.0F) {
            this.fade += 0.05F;
        } else if (!isGliding && this.fade > 0.0F) {
            this.fade -= 0.05F;
        }

        if (this.fade <= 0.0F) {
            this.stop();
            return;
        }

        this.x = (float) this.player.getX();
        this.y = (float) this.player.getY();
        this.z = (float) this.player.getZ();

        float speed = (float) this.player.getDeltaMovement().length();

        float baseVolume = 0.0F;
        if (speed >= 0.01F) {
            baseVolume = Mth.clamp(speed * 2.0F, 0.1F, 0.3F);

            this.pitch = 1.0F;
        }

        this.volume = baseVolume * this.fade;
    }
}