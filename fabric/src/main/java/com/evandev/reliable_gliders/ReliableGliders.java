package com.evandev.reliable_gliders;

import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

public class ReliableGliders implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glider"), ModItems.GLIDER);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.GLIDER);
        });
    }
}