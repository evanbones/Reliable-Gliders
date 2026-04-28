package com.evandev.reliable_gliders.content;

import com.evandev.reliable_gliders.item.GliderItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NeoForgeGliderItem extends GliderItem {
    public NeoForgeGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldCauseReequipAnimation(@NotNull ItemStack oldStack, @NotNull ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }
}