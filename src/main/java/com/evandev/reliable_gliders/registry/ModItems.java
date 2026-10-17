package com.evandev.reliable_gliders.registry;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.item.GliderItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

//? if >=26.1 {
/*import com.evandev.reliable_gliders.config.ModConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.Equippable;
*///?}

public class ModItems {
    public static final ResourceLocation GLIDER_ID = Constants.id("glider");

    public static Item GLIDER;

    public static void init(BiConsumer<ResourceLocation, Item> registrar) {
        GLIDER = new GliderItem(gliderProperties());
        registrar.accept(GLIDER_ID, GLIDER);
    }

    private static Item.Properties gliderProperties() {
        //? if >=26.1 {
        /*Item.Properties props = new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, GLIDER_ID))
                .durability(864)
                .repairable(ModTags.Items.GLIDER_REPAIR_ITEMS);

        if (ModConfig.get().equipToChestplate) {
            props.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST)
                    .setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
                    .build());
        }
        return props;
        *///?} else {
        return new Item.Properties().durability(864);
        //?}
    }
}
