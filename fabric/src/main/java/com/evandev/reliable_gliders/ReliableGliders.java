package com.evandev.reliable_gliders;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.item.CreativeModeTabs;

public class ReliableGliders implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        ModItems.init();

        PayloadTypeRegistry.serverboundPlay().register(SetGliderStatePayload.TYPE, SetGliderStatePayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SetGliderStatePayload.TYPE, (payload, context) -> {
            context.player().level().getServer().execute(() -> {
                GlidingState.setGliding(context.player(), payload.isGliding());
            });
        });

        PayloadTypeRegistry.serverboundPlay().register(SyncGliderSettingsPayload.TYPE, SyncGliderSettingsPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SyncGliderSettingsPayload.TYPE, (payload, context) -> {
            context.player().level().getServer().execute(() -> {
                GlidingState.setKeyBound(context.player(), payload.isKeyBound());
            });
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.GLIDER);
        });
    }
}