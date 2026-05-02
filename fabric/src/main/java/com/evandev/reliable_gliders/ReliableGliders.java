package com.evandev.reliable_gliders;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.item.CreativeModeTabs;

public class ReliableGliders implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        ModItems.init();

        PayloadTypeRegistry.playC2S().register(SetGliderStatePayload.TYPE, SetGliderStatePayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SetGliderStatePayload.TYPE, (payload, context) -> {
            context.player().server.execute(() -> {
                GlidingState.setGliding(context.player(), payload.isGliding());
            });
        });

        PayloadTypeRegistry.playC2S().register(SyncGliderSettingsPayload.TYPE, SyncGliderSettingsPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SyncGliderSettingsPayload.TYPE, (payload, context) -> {
            context.player().server.execute(() -> {
                GlidingState.setKeyBound(context.player(), payload.isKeyBound());
            });
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.GLIDER);
        });
    }
}