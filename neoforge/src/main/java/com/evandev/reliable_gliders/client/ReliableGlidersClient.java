package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.NeoForge;

public class ReliableGlidersClient {
    private static boolean wasDeployKeyDown = false;
    private static boolean wasOnGroundLastTick = true;

    public static void register(ModContainer container, IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ClothConfigIntegration.createScreen(parent));
        }
        modEventBus.addListener(ReliableGlidersClient::registerKeyMappings);

        NeoForge.EVENT_BUS.addListener(ReliableGlidersClient::onClientTick);
    }

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ClientConstants.DEPLOY_KEY);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (mc.player.tickCount % 20 == 0) {
            boolean currentBoundState = !ClientConstants.DEPLOY_KEY.isUnbound();
            GlidingState.setKeyBound(mc.player, currentBoundState);
            ClientPacketDistributor.sendToServer(new SyncGliderSettingsPayload(currentBoundState));
        }

        while (ClientConstants.DEPLOY_KEY.consumeClick()) {
        }

        boolean isDeployKeyDown = ClientConstants.DEPLOY_KEY.isDown();

        if (!isDeployKeyDown && !ClientConstants.DEPLOY_KEY.isUnbound()) {
            String deployKeyString = ClientConstants.DEPLOY_KEY.saveString();
            for (KeyMapping key : mc.options.keyMappings) {
                if (key.saveString().equals(deployKeyString) && key.isDown()) {
                    isDeployKeyDown = true;
                    break;
                }
            }
        }

        boolean isOnGroundNow = mc.player.onGround();

        if (isDeployKeyDown && !wasDeployKeyDown) {
            if (!wasOnGroundLastTick && !isOnGroundNow) {
                boolean newState = !GlidingState.wasGliding(mc.player);
                GlidingState.setGliding(mc.player, newState);
                ClientPacketDistributor.sendToServer(new SetGliderStatePayload(newState));
            }
        }

        wasDeployKeyDown = isDeployKeyDown;
        wasOnGroundLastTick = isOnGroundNow;
    }
}