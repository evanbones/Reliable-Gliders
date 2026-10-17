package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.item.GliderItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class GliderClient {
    private static boolean wasDeployKeyDown = false;
    private static boolean wasOnGroundLastTick = true;

    public static int getGliderTint(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            int color = GliderItem.getDyeColor(stack);
            return color != -1 ? (0xFF000000 | color) : -1;
        }
        return -1;
    }

    public static void onClientTick(Minecraft client) {
        if (client.player == null) return;

        if (client.player.tickCount % 20 == 0) {
            boolean currentBoundState = !ClientConstants.DEPLOY_KEY.isUnbound();
            GlidingState.setKeyBound(client.player, currentBoundState);
            ClientNetworking.sendKeyBound(currentBoundState);
        }

        while (ClientConstants.DEPLOY_KEY.consumeClick()) {
        }

        boolean isDeployKeyDown = ClientConstants.DEPLOY_KEY.isDown();

        // Vanilla only marks one mapping as pressed when several share a key, so check the others too.
        if (!isDeployKeyDown && !ClientConstants.DEPLOY_KEY.isUnbound()) {
            String deployKeyString = ClientConstants.DEPLOY_KEY.saveString();
            for (KeyMapping key : client.options.keyMappings) {
                if (key.saveString().equals(deployKeyString) && key.isDown()) {
                    isDeployKeyDown = true;
                    break;
                }
            }
        }

        boolean isOnGroundNow = client.player.onGround();

        if (isDeployKeyDown && !wasDeployKeyDown) {
            if (!wasOnGroundLastTick && !isOnGroundNow) {
                boolean newState = !GlidingState.wasGliding(client.player);
                GlidingState.setGliding(client.player, newState);
                ClientNetworking.sendGliderState(newState);
            }
        }

        wasDeployKeyDown = isDeployKeyDown;
        wasOnGroundLastTick = isOnGroundNow;
    }
}
