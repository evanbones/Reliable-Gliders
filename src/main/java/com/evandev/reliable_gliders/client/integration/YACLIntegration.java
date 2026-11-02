package com.evandev.reliable_gliders.client.integration;

import com.evandev.reliable_gliders.config.ModConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class YACLIntegration {

    public static Screen createScreen(Screen parent) {
        ModConfig config = ModConfig.get();

        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.reliable_gliders.title"))
                .save(ModConfig::save);

        ConfigCategory.Builder general = ConfigCategory.createBuilder()
                .name(Component.translatable("config.reliable_gliders.category.general"))
                .option(createDoubleSliderOption("updraft_strength", 0.7, 0.0, 5.0, 0.05,
                        () -> config.updraftStrength, val -> config.updraftStrength = val))
                .option(createIntSliderOption("updraft_height", 15, 1, 384, 1,
                        () -> config.updraftHeight, val -> config.updraftHeight = val))
                .option(createDoubleSliderOption("horizontal_speed", 1.0, 0.0, 10.0, 0.05,
                        () -> config.horizontalSpeed, val -> config.horizontalSpeed = val))
                .option(createBoolOption("equip_to_chestplate", false,
                        () -> config.equipToChestplate, val -> config.equipToChestplate = val))
                .option(createBoolOption("disable_in_creative", false,
                        () -> config.disableInCreative, val -> config.disableInCreative = val))
                .group(ListOption.<String>createBuilder()
                        .name(Component.translatable("config.reliable_gliders.updraft_disabled_dimensions"))
                        .description(OptionDescription.of(Component.translatable("config.reliable_gliders.updraft_disabled_dimensions.tooltip")))
                        .binding(List.of("minecraft:the_nether"), () -> config.updraftDisabledDimensions,
                                val -> config.updraftDisabledDimensions = new java.util.ArrayList<>(val))
                        .controller(StringControllerBuilder::create)
                        .initial("")
                        .build());

        return builder.category(general.build()).build().generateScreen(parent);
    }

    private static Option<Boolean> createBoolOption(String name, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable("config.reliable_gliders." + name))
                .description(OptionDescription.of(Component.translatable("config.reliable_gliders." + name + ".tooltip")))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }

    private static Option<Double> createDoubleSliderOption(String name, double defaultValue, double min, double max, double step, Supplier<Double> getter, Consumer<Double> setter) {
        return Option.<Double>createBuilder()
                .name(Component.translatable("config.reliable_gliders." + name))
                .description(OptionDescription.of(Component.translatable("config.reliable_gliders." + name + ".tooltip")))
                .binding(defaultValue, getter, setter)
                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(min, max).step(step))
                .build();
    }

    private static Option<Integer> createIntSliderOption(String name, int defaultValue, int min, int max, int step, Supplier<Integer> getter, Consumer<Integer> setter) {
        return Option.<Integer>createBuilder()
                .name(Component.translatable("config.reliable_gliders." + name))
                .description(OptionDescription.of(Component.translatable("config.reliable_gliders." + name + ".tooltip")))
                .binding(defaultValue, getter, setter)
                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(min, max).step(step))
                .build();
    }
}
