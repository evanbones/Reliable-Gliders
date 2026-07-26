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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
                serverPlayer.connection.aboveGroundTickCount = 0;
                serverPlayer.connection.aboveGroundVehicleTickCount = 0;

                if (serverPlayer.level().getGameTime() % 20 == 0) {
                    boolean damaged = false;
                    if (serverPlayer.getMainHandItem().is(ModItems.GLIDER)) {
                        serverPlayer.getMainHandItem().hurtAndBreak(1, serverPlayer, EquipmentSlot.MAINHAND);
                        damaged = true;
                    } else if (serverPlayer.getOffhandItem().is(ModItems.GLIDER)) {
                        serverPlayer.getOffhandItem().hurtAndBreak(1, serverPlayer, EquipmentSlot.OFFHAND);
                        damaged = true;
                    } else if (ModConfig.get().equipToChestplate && serverPlayer.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.GLIDER)) {
                        serverPlayer.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak(1, serverPlayer, EquipmentSlot.CHEST);
                        damaged = true;
                    }

                    if (!damaged) {
                        Services.PLATFORM.damageGliderInAccessorySlot(serverPlayer);
                    }
                }
            }

            if (!wasGliding) {
                player.level().playSound(player, player.blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 0.85f);
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

            double horizontalSpeed = ModConfig.get().horizontalSpeed;
            double newX = player.getDeltaMovement().x;
            double newZ = player.getDeltaMovement().z;

            if (horizontalSpeed != 1.0) {
                newX *= horizontalSpeed;
                newZ *= horizontalSpeed;

                double maxSpeed = 0.5 * Math.max(1.0, horizontalSpeed);
                double currentHorizontalSpeed = Math.hypot(newX, newZ);
                if (currentHorizontalSpeed > maxSpeed) {
                    double scale = maxSpeed / currentHorizontalSpeed;
                    newX *= scale;
                    newZ *= scale;
                }
            }

            player.setDeltaMovement(newX, newY, newZ);
        } else {
            if (wasGliding) {
                GlidingState.setGliding(player, false);
            }
        }
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$preventAttacking(Entity entity, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (GlidingState.isGliding(player) && !player.getMainHandItem().is(ModTags.Items.GLIDER_USABLE_ITEMS)) {
            ci.cancel();
        }
    }
}