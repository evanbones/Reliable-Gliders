package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.item.GliderItem;
import com.evandev.reliable_gliders.platform.Services;
import com.evandev.reliable_gliders.registry.ModItems;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void reliableGliders$tickGliderPhysics(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        boolean isGliding = GlidingState.isGliding(player);
        boolean wasGliding = GlidingState.wasGliding(player);

        if (isGliding) {
            if (player.isUsingItem() && !player.getUseItem().is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
                player.stopUsingItem();
            }

            player.fallDistance = 0.0F;
            player.setSprinting(false);

            if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                ServerGamePacketListenerImplAccessor connection = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                connection.reliableGliders$setAboveGroundTickCount(0);
                connection.reliableGliders$setAboveGroundVehicleTickCount(0);

                if (serverPlayer.level().getGameTime() % 20 == 0) {
                    boolean damaged = reliableGliders$damageGlider(serverPlayer, EquipmentSlot.MAINHAND)
                            || reliableGliders$damageGlider(serverPlayer, EquipmentSlot.OFFHAND)
                            || (ModConfig.get().equipToChestplate && reliableGliders$damageGlider(serverPlayer, EquipmentSlot.CHEST));

                    if (!damaged) {
                        Services.PLATFORM.damageGliderInAccessorySlot(serverPlayer);
                    }
                }
            }

            if (!wasGliding) {
                //? if >=1.21 {
                player.level().playSound(player, player.blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 0.85f);
                //?} else {
                /*player.level().playSound(player, player.blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1.0f, 0.85f);
                *///?}
            }

            GlidingState.setGliding(player, true);

            double currentY = player.getDeltaMovement().y;
            double newY;

            if (GliderItem.hasUpdraft(player)) {
                newY = Math.max(currentY, ModConfig.get().updraftStrength);
                newY = Mth.lerp(0.2, currentY, newY);
            } else {
                newY = Math.max(currentY, -0.05);
            }

            player.setDeltaMovement(player.getDeltaMovement().x, newY, player.getDeltaMovement().z);
        } else {
            if (wasGliding) {
                GlidingState.setGliding(player, false);
            }
        }
    }

    @Inject(method = "getFlyingSpeed", at = @At("RETURN"), cancellable = true)
    private void reliableGliders$scaleGlidingSpeed(CallbackInfoReturnable<Float> cir) {
        double horizontalSpeed = ModConfig.get().horizontalSpeed;
        if (horizontalSpeed != 1.0 && GlidingState.wasGliding((Player) (Object) this)) {
            cir.setReturnValue((float) (cir.getReturnValue() * horizontalSpeed));
        }
    }

    @Unique
    private static boolean reliableGliders$damageGlider(ServerPlayer player, EquipmentSlot slot) {
        ItemStack stack = player.getItemBySlot(slot);
        if (!stack.is(ModItems.GLIDER)) {
            return false;
        }
        //? if >=1.21 {
        stack.hurtAndBreak(1, player, slot);
        //?} else {
        /*stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(slot));
        *///?}
        return true;
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$preventAttacking(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (GlidingState.isGliding(player) && !player.getMainHandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            ci.cancel();
        }
    }
}
