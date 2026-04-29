package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.DyedItemColor;

public class ReliableGlidersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModels(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/glider_3d"));
        });

        ItemProperties.register(ModItems.GLIDER, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gliding"),
                (stack, level, entity, seed) -> {
                    if (entity instanceof Player player && GlidingState.isGliding(player)) {
                        return 1.0F;
                    }
                    return 0.0F;
                });

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 0) {
                DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
                return color != null ? (0xFF000000 | color.rgb()) : -1;
            }
            return -1;
        }, ModItems.GLIDER);
    }
}