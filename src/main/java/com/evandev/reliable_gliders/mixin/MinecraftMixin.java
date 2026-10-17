package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelAttack(CallbackInfoReturnable<Boolean> cir) {
        if (this.player != null && GlidingState.isGliding(this.player) && !this.player.getMainHandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelContinueAttack(boolean down, CallbackInfo ci) {
        if (this.player != null && GlidingState.isGliding(this.player) && !this.player.getMainHandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            ci.cancel();
        }
    }

    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelUseItem(CallbackInfo ci) {
        if (this.player != null && GlidingState.isGliding(this.player)) {
            boolean canUse = this.player.getMainHandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS) ||
                    this.player.getOffhandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS);
            if (!canUse) {
                ci.cancel();
            }
        }
    }
}