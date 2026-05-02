package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.DyedItemColor;

public class ReliableGlidersClient implements ClientModInitializer {

    private static boolean wasDeployKeyDown = false;
    private static boolean wasOnGroundLastTick = true;

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

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 0) {
                DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
                return color != null ? (0xFF000000 | color.rgb()) : -1;
            }
            return -1;
        }, ModItems.GLIDER);

        KeyBindingHelper.registerKeyBinding(ClientConstants.DEPLOY_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (client.player.tickCount % 20 == 0) {
                boolean currentBoundState = !ClientConstants.DEPLOY_KEY.isUnbound();
                GlidingState.setKeyBound(client.player, currentBoundState);
                if (ClientPlayNetworking.canSend(SyncGliderSettingsPayload.TYPE)) {
                    ClientPlayNetworking.send(new SyncGliderSettingsPayload(currentBoundState));
                }
            }

            while (ClientConstants.DEPLOY_KEY.consumeClick()) {}

            boolean isDeployKeyDown = ClientConstants.DEPLOY_KEY.isDown();

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
                    if (ClientPlayNetworking.canSend(SetGliderStatePayload.TYPE)) {
                        ClientPlayNetworking.send(new SetGliderStatePayload(newState));
                    }
                }
            }

            wasDeployKeyDown = isDeployKeyDown;
            wasOnGroundLastTick = isOnGroundNow;
        });
    }
}