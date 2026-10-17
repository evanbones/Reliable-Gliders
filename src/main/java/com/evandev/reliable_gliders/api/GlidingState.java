package com.evandev.reliable_gliders.api;

import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.item.GliderItem;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class GlidingState {

    private static final Map<Player, Boolean> GLIDING_PLAYERS = Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<Player, Boolean> KEY_BOUND_PLAYERS = Collections.synchronizedMap(new WeakHashMap<>());

    public static void setGliding(Player player, boolean gliding) {
        if (gliding) {
            GLIDING_PLAYERS.put(player, true);
        } else {
            GLIDING_PLAYERS.remove(player);
        }
    }

    public static boolean wasGliding(Player player) {
        return GLIDING_PLAYERS.getOrDefault(player, false);
    }

    public static void setKeyBound(Player player, boolean bound) {
        if (bound) {
            KEY_BOUND_PLAYERS.put(player, true);
        } else {
            KEY_BOUND_PLAYERS.remove(player);
        }
    }

    public static boolean isKeyBound(Player player) {
        return KEY_BOUND_PLAYERS.getOrDefault(player, false);
    }

    public static ItemStack getGliderStack(Player player) {
        if (player.getMainHandItem().getItem() instanceof GliderItem) {
            return player.getMainHandItem();
        }
        if (player.getOffhandItem().getItem() instanceof GliderItem) {
            return player.getOffhandItem();
        }
        if (ModConfig.get().equipToChestplate && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GliderItem) {
            return player.getItemBySlot(EquipmentSlot.CHEST);
        }
        return Services.PLATFORM.getGliderStackInAccessorySlot(player);
    }

    public static boolean isGliding(Player player) {
        boolean holdingGlider = player.getMainHandItem().getItem() instanceof GliderItem ||
                player.getOffhandItem().getItem() instanceof GliderItem ||
                (ModConfig.get().equipToChestplate && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GliderItem) ||
                Services.PLATFORM.isGliderEquippedInAccessorySlot(player);

        if (!holdingGlider || player.onGround() || player.isFallFlying() || player.isInWater() || player.onClimbable() || player.isPassenger()) {
            if (wasGliding(player)) {
                setGliding(player, false);
            }
            return false;
        }

        if (isKeyBound(player)) {
            return wasGliding(player);
        }

        if (wasGliding(player)) {
            return true;
        }

        boolean isFalling = player.getDeltaMovement().y < 0 || (player.level().isClientSide() && player.getY() < player.yOld);
        boolean hasUpdraft = GliderItem.hasUpdraft(player);

        if (isFalling && !hasUpdraft) {
            AABB clearanceBox = player.getBoundingBox().move(0, -2, 0).inflate(-0.1, 0, -0.1);
            boolean hasClearance = player.level().noCollision(player, clearanceBox);
            if (!hasClearance) {
                return false;
            }
        }

        boolean shouldGlide = isFalling || hasUpdraft;
        if (shouldGlide) {
            setGliding(player, true);
        }

        return shouldGlide;
    }
}