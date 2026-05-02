package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.network.ToggleGliderPayload;
import com.evandev.reliable_gliders.platform.Services;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class ReliableGlidersClient {

    public static void register(ModContainer container, IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ClothConfigIntegration.createScreen(parent));
        }
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ClientConstants.DEPLOY_KEY);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (ClientConstants.DEPLOY_KEY.consumeClick()) {
            ClientPacketDistributor.sendToServer(new ToggleGliderPayload());
        }
    }
}