package com.evandev.reliable_gliders.client.integration;

import com.evandev.reliable_gliders.config.ModConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {

    public static Screen createScreen(Screen parent) {
        ModConfig config = ModConfig.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.reliable_gliders.title"));

        builder.setSavingRunnable(ModConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.reliable_gliders.category.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startDoubleField(Component.translatable("config.reliable_gliders.updraft_strength"), config.updraftStrength)
                .setDefaultValue(0.7)
                .setMin(0.0)
                .setMax(5.0)
                .setTooltip(Component.translatable("config.reliable_gliders.updraft_strength.tooltip"))
                .setSaveConsumer(newValue -> config.updraftStrength = newValue)
                .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.reliable_gliders.updraft_height"), config.updraftHeight)
                .setDefaultValue(15)
                .setMin(1)
                .setMax(384)
                .setTooltip(Component.translatable("config.reliable_gliders.updraft_height.tooltip"))
                .setSaveConsumer(newValue -> config.updraftHeight = newValue)
                .build());

        return builder.build();
    }
}