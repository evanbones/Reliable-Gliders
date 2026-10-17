package com.evandev.reliable_gliders.compat;

//? if >=26.1 {
/*import com.evandev.reliable_gliders.registry.ModItems;
import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TrinketsUpdatedCompat {
    public static final String MOD_ID = "trinkets_updated";

    public static ItemStack getGlider(Player player) {
        TrinketAttachment attachment = TrinketsApi.getAttachment(player);
        if (attachment == null) return ItemStack.EMPTY;

        ItemStack[] found = {ItemStack.EMPTY};
        attachment.forEach((access, stack) -> {
            if (found[0].isEmpty() && stack.is(ModItems.GLIDER)) {
                found[0] = stack;
            }
        });
        return found[0];
    }

    public static void damageGlider(ServerPlayer player) {
        TrinketAttachment attachment = TrinketsApi.getAttachment(player);
        if (attachment == null) return;

        attachment.forEach((access, stack) -> {
            if (stack.is(ModItems.GLIDER)) {
                TrinketsApi.hurtAndBreakItemStack(stack, 1, player, access);
            }
        });
    }
}
*///?}
