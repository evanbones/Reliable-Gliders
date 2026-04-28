package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ReliableGlidersClient {
    public static void register(ModContainer container, IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ClothConfigIntegration.createScreen(parent));
        }

        modEventBus.addListener(ReliableGlidersClient::onModelRegister);
    }

    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/glider_3d")));
    }
}