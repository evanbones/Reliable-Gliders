package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "handleBlockBreakAction", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelBlockBreak(BlockPos pos, ServerboundPlayerActionPacket.Action action, Direction direction, int maxY, int sequence, CallbackInfo ci) {
        if (GlidingState.isGliding(this.player) && !this.player.getMainHandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            ci.cancel();
        }
    }

    @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelItemUse(ServerPlayer player, Level level, ItemStack itemStack, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (GlidingState.isGliding(player) && !itemStack.is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelItemUseOn(ServerPlayer player, Level level, ItemStack itemStack, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (GlidingState.isGliding(player) && !itemStack.is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}