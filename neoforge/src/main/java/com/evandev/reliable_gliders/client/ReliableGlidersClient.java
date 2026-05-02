package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.network.ToggleGliderPayload;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class ReliableGlidersClient {
    private static boolean lastKeyBoundState = false;

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
        Minecraft mc = Minecraft.getInstance();

        boolean currentBoundState = !ClientConstants.DEPLOY_KEY.isUnbound();
        if (currentBoundState != lastKeyBoundState) {
            lastKeyBoundState = currentBoundState;
            if (mc.player != null) {
                GlidingState.setKeyBound(mc.player, currentBoundState);
            }
            ClientPacketDistributor.sendToServer(new SyncGliderSettingsPayload(currentBoundState));
        }

        while (ClientConstants.DEPLOY_KEY.consumeClick()) {
            if (mc.player != null) {
                GlidingState.setGliding(mc.player, !GlidingState.wasGliding(mc.player));
            }
            ClientPacketDistributor.sendToServer(new ToggleGliderPayload());
        }
    }
}