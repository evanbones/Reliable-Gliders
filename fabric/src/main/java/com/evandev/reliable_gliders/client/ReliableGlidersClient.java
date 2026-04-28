package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

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
    }
}