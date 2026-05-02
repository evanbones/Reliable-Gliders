package com.evandev.reliable_gliders.registry;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static Item GLIDER;

    public static void init() {
        GLIDER = Services.PLATFORM.createGliderItem(new Item.Properties().durability(400));
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glider"), GLIDER);
    }
}