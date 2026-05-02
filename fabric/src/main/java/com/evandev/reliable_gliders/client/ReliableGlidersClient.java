package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ReliableGlidersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(ClientConstants.DEPLOY_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (client.player.tickCount % 20 == 0) {
                boolean currentBoundState = !ClientConstants.DEPLOY_KEY.isUnbound();
                GlidingState.setKeyBound(client.player, currentBoundState);
                if (ClientPlayNetworking.canSend(SyncGliderSettingsPayload.TYPE)) {
                    ClientPlayNetworking.send(new SyncGliderSettingsPayload(currentBoundState));
                }
            }

            while (ClientConstants.DEPLOY_KEY.consumeClick()) {
                boolean newState = !GlidingState.wasGliding(client.player);
                GlidingState.setGliding(client.player, newState);
                if (ClientPlayNetworking.canSend(SetGliderStatePayload.TYPE)) {
                    ClientPlayNetworking.send(new SetGliderStatePayload(newState));
                }
            }
        });
    }
}