package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.network.ToggleGliderPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ReliableGlidersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(ClientConstants.DEPLOY_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ClientConstants.DEPLOY_KEY.consumeClick()) {
                if (ClientPlayNetworking.canSend(ToggleGliderPayload.TYPE)) {
                    ClientPlayNetworking.send(new ToggleGliderPayload());
                }
            }
        });
    }
}