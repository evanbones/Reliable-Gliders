package com.evandev.reliable_gliders.compat;

//? if forgelike {
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class CuriosCompat {

    public static ItemStack getGlider(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(inv -> inv.findCurios(ModItems.GLIDER))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).stack())
                .orElse(ItemStack.EMPTY);
    }

    public static void damageGlider(ServerPlayer player) {
        CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
            List<SlotResult> results = inv.findCurios(ModItems.GLIDER);
            for (SlotResult result : results) {
                //? if >=26.1 {
                /*result.stack().hurtAndBreak(1, player.level(), player, item -> CuriosApi.broadcastCurioBreakEvent(result.slotContext()));
                *///?} else if >=1.21 {
                result.stack().hurtAndBreak(1, player.serverLevel(), player, item -> CuriosApi.broadcastCurioBreakEvent(result.slotContext()));
                //?} else {
                /*result.stack().hurtAndBreak(1, player, p -> CuriosApi.broadcastCurioBreakEvent(result.slotContext()));
                *///?}
            }
        });
    }
}
//?}
