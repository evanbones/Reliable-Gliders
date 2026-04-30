package com.evandev.reliable_gliders.content;

import com.evandev.reliable_gliders.item.GliderItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class FabricGliderItem extends GliderItem implements FabricItem {
    public FabricGliderItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowComponentsUpdateAnimation(@NonNull Player player, @NonNull InteractionHand hand, @NonNull ItemStack oldStack, @NonNull ItemStack newStack) {
        return false;
    }
}