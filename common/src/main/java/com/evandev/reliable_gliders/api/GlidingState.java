package com.evandev.reliable_gliders.api;

import com.evandev.reliable_gliders.item.GliderItem;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class GlidingState {

    private static final Map<Player, Boolean> GLIDING_PLAYERS = new WeakHashMap<>();

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

    public static boolean isGliding(Player player) {
        boolean holdingGlider = player.getMainHandItem().getItem() instanceof GliderItem ||
                player.getOffhandItem().getItem() instanceof GliderItem;

        if (!holdingGlider || player.onGround() || player.isFallFlying() || player.isInWater()) {
            if (wasGliding(player)) {
                setGliding(player, false);
            }
            return false;
        }

        if (wasGliding(player)) {
            return true;
        }

        return player.getDeltaMovement().y < 0 || GliderItem.hasUpdraft(player);
    }
}