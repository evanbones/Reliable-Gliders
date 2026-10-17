package com.evandev.reliable_gliders.compat;

//? if fabric && <26.1 {
/*import com.evandev.reliable_gliders.registry.ModItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrinketsCompat {

    public static ItemStack getGlider(Player player) {
        return TrinketsApi.getTrinketComponent(player)
                .map(component -> component.getEquipped(ModItems.GLIDER))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).getB())
                .orElse(ItemStack.EMPTY);
    }

    public static void damageGlider(ServerPlayer player) {
        TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
            List<Tuple<SlotReference, ItemStack>> equipped = component.getEquipped(ModItems.GLIDER);
            for (Tuple<SlotReference, ItemStack> tuple : equipped) {
                ItemStack stack = tuple.getB();
                //? if >=1.21 {
                stack.hurtAndBreak(1, player.serverLevel(), player, item -> TrinketsApi.onTrinketBroken(stack, tuple.getA(), player));
                //?} else {
                /^stack.hurtAndBreak(1, player, p -> TrinketsApi.onTrinketBroken(stack, tuple.getA(), p));
                ^///?}
            }
        });
    }
}
*///?}
