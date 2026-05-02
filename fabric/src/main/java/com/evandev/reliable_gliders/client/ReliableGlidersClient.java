package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.network.ToggleGliderPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ReliableGlidersClient implements ClientModInitializer {
    private static boolean lastKeyBoundState = false;

    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(ClientConstants.DEPLOY_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean currentBoundState = !ClientConstants.DEPLOY_KEY.isUnbound();
            if (currentBoundState != lastKeyBoundState) {
                lastKeyBoundState = currentBoundState;
                if (client.player != null) {
                    GlidingState.setKeyBound(client.player, currentBoundState);
                }
                if (ClientPlayNetworking.canSend(SyncGliderSettingsPayload.TYPE)) {
                    ClientPlayNetworking.send(new SyncGliderSettingsPayload(currentBoundState));
                }
            }

            while (ClientConstants.DEPLOY_KEY.consumeClick()) {
                if (client.player != null) {
                    GlidingState.setGliding(client.player, !GlidingState.wasGliding(client.player));
                }
                if (ClientPlayNetworking.canSend(ToggleGliderPayload.TYPE)) {
                    ClientPlayNetworking.send(new ToggleGliderPayload());
                }
            }
        });
    }
}