package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;

public class ReliableGlidersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModels(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/glider_3d"));
        });
    }
}