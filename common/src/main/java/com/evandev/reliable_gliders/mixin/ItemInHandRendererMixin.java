package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Shadow
    private ItemStack mainHandItem;
    @Shadow
    private ItemStack offHandItem;

    @Unique
    private ItemStack reliableGliders$storedMain;
    @Unique
    private ItemStack reliableGliders$storedOff;
    @Unique
    private boolean reliableGliders$modified = false;

    @Inject(method = "renderHandsWithItems", at = @At("HEAD"))
    private void reliableGliders$setupGliderHands(float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer playerEntity, int combinedLight, CallbackInfo ci) {
        if (GlidingState.isGliding(playerEntity)) {
            boolean holdingGlider = this.mainHandItem.is(ModItems.GLIDER) || this.offHandItem.is(ModItems.GLIDER);

            if (!holdingGlider) {
                this.reliableGliders$storedMain = this.mainHandItem;
                this.reliableGliders$storedOff = this.offHandItem;

                this.mainHandItem = new ItemStack(ModItems.GLIDER);
                this.offHandItem = ItemStack.EMPTY;
                this.reliableGliders$modified = true;
            }
        }
    }

    @Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void reliableGliders$restoreGliderHands(float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer playerEntity, int combinedLight, CallbackInfo ci) {
        if (this.reliableGliders$modified) {
            this.mainHandItem = this.reliableGliders$storedMain;
            this.offHandItem = this.reliableGliders$storedOff;

            this.reliableGliders$modified = false;
            this.reliableGliders$storedMain = null;
            this.reliableGliders$storedOff = null;
        }
    }
}