package com.evandev.reliable_gliders;

import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ReliableGliders implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        ModItems.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.GLIDER);
        });
    }
}