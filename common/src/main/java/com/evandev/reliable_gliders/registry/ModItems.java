package com.evandev.reliable_gliders.registry;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;

public class ModItems {

    public static Item GLIDER;

    public static void init() {
        ResourceKey<Item> gliderKey = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "glider")
        );

        Item.Properties props = new Item.Properties()
                .setId(gliderKey)
                .durability(400)
                .repairable(ModTags.Items.GLIDER_REPAIR_ITEMS);

        if (ModConfig.get().equipToChestplate) {
            props.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST)
                    .setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
                    .build());
        }

        GLIDER = Services.PLATFORM.createGliderItem(props);
        Registry.register(BuiltInRegistries.ITEM, gliderKey.identifier(), GLIDER);
    }
}