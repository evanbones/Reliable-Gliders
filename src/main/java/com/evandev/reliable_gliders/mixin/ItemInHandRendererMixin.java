package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.3 {
/*import net.minecraft.client.player.FirstPersonHandsAndItems;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
*///?} else {
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
//?}
//? if >=26.1 && <26.3 {
/*import net.minecraft.client.renderer.SubmitNodeCollector;
*///?} else if <26.1 {
import net.minecraft.client.renderer.MultiBufferSource;
//?}

/**
 * Shows the glider in first person while gliding with it in an accessory or armor slot
 */
//? if >=26.3 {
/*@Mixin(FirstPersonHandsAndItems.class)
*///?} else {
@Mixin(ItemInHandRenderer.class)
//?}
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

    //? if >=26.3 {
    /*@Inject(method = "extractRenderState", at = @At("HEAD"))
    private void reliableGliders$setupGliderHands(LocalPlayer player, float partialTicks, FirstPersonHandsAndItemsRenderState state, CallbackInfo ci) {
    *///?} else if >=26.2 {
    /*@Inject(method = "submitHandsWithItems", at = @At("HEAD"))
    private void reliableGliders$setupGliderHands(float frameInterp, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LocalPlayer player, int lightCoords, CallbackInfo ci) {
    *///?} else if >=26.1 {
    /*@Inject(method = "renderHandsWithItems", at = @At("HEAD"))
    private void reliableGliders$setupGliderHands(float frameInterp, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LocalPlayer player, int lightCoords, CallbackInfo ci) {
    *///?} else {
    @Inject(method = "renderHandsWithItems", at = @At("HEAD"))
    private void reliableGliders$setupGliderHands(float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer player, int combinedLight, CallbackInfo ci) {
    //?}
        if (GlidingState.isGliding(player)) {
            boolean holdingGlider = this.mainHandItem.is(ModItems.GLIDER) || this.offHandItem.is(ModItems.GLIDER);

            if (!holdingGlider) {
                this.reliableGliders$storedMain = this.mainHandItem;
                this.reliableGliders$storedOff = this.offHandItem;

                ItemStack gliderStack = GlidingState.getGliderStack(player);
                this.mainHandItem = gliderStack.isEmpty() ? new ItemStack(ModItems.GLIDER) : gliderStack;
                this.offHandItem = ItemStack.EMPTY;
                this.reliableGliders$modified = true;
            }
        }
    }

    //? if >=26.3 {
    /*@Inject(method = "extractRenderState", at = @At("TAIL"))
    private void reliableGliders$restoreGliderHands(LocalPlayer player, float partialTicks, FirstPersonHandsAndItemsRenderState state, CallbackInfo ci) {
    *///?} else if >=26.2 {
    /*@Inject(method = "submitHandsWithItems", at = @At("TAIL"))
    private void reliableGliders$restoreGliderHands(float frameInterp, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LocalPlayer player, int lightCoords, CallbackInfo ci) {
    *///?} else if >=26.1 {
    /*@Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void reliableGliders$restoreGliderHands(float frameInterp, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LocalPlayer player, int lightCoords, CallbackInfo ci) {
    *///?} else {
    @Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void reliableGliders$restoreGliderHands(float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer player, int combinedLight, CallbackInfo ci) {
    //?}
        if (this.reliableGliders$modified) {
            this.mainHandItem = this.reliableGliders$storedMain;
            this.offHandItem = this.reliableGliders$storedOff;

            this.reliableGliders$modified = false;
            this.reliableGliders$storedMain = null;
            this.reliableGliders$storedOff = null;
        }
    }
}
