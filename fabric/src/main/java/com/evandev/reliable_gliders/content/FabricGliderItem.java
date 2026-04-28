package com.evandev.reliable_gliders.content;

import com.evandev.reliable_gliders.item.GliderItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FabricGliderItem extends GliderItem implements FabricItem {
    public FabricGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
}