package com.evandev.reliable_gliders;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.ToggleGliderPayload;
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
        PayloadTypeRegistry.serverboundPlay().register(ToggleGliderPayload.TYPE, ToggleGliderPayload.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ToggleGliderPayload.TYPE, (payload, context) -> {
            context.player().level().getServer().execute(() -> {
                GlidingState.setGliding(context.player(), !GlidingState.wasGliding(context.player()));
            });
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.GLIDER);
        });
    }
}