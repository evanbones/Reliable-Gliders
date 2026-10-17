package com.evandev.reliable_gliders.fabric;

//? if fabric {
/*import com.evandev.reliable_gliders.CommonClass;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;

//? if >=26.1 {
/^import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
^///?} else {
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//?}
//? if >=1.21 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//?}

public class ReliableGlidersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        ModItems.init((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));

        registerPayloads();

        //? if >=26.1 {
        /^CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(ModItems.GLIDER));
        ^///?} else {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(ModItems.GLIDER));
        //?}
    }

    private static void registerPayloads() {
        //? if >=26.1 {
        /^PayloadTypeRegistry.serverboundPlay().register(SetGliderStatePayload.TYPE, SetGliderStatePayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SyncGliderSettingsPayload.TYPE, SyncGliderSettingsPayload.STREAM_CODEC);
        ^///?} else if >=1.21 {
        PayloadTypeRegistry.playC2S().register(SetGliderStatePayload.TYPE, SetGliderStatePayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(SyncGliderSettingsPayload.TYPE, SyncGliderSettingsPayload.STREAM_CODEC);
        //?}

        //? if >=1.21 {
        ServerPlayNetworking.registerGlobalReceiver(SetGliderStatePayload.TYPE, (payload, context) ->
                context.player().level().getServer().execute(() -> GlidingState.setGliding(context.player(), payload.isGliding())));
        ServerPlayNetworking.registerGlobalReceiver(SyncGliderSettingsPayload.TYPE, (payload, context) ->
                context.player().level().getServer().execute(() -> GlidingState.setKeyBound(context.player(), payload.isKeyBound())));
        //?} else {
        /^ServerPlayNetworking.registerGlobalReceiver(SetGliderStatePayload.ID, (server, player, handler, buf, responseSender) -> {
            SetGliderStatePayload payload = SetGliderStatePayload.read(buf);
            server.execute(() -> GlidingState.setGliding(player, payload.isGliding()));
        });
        ServerPlayNetworking.registerGlobalReceiver(SyncGliderSettingsPayload.ID, (server, player, handler, buf, responseSender) -> {
            SyncGliderSettingsPayload payload = SyncGliderSettingsPayload.read(buf);
            server.execute(() -> GlidingState.setKeyBound(player, payload.isKeyBound()));
        });
        ^///?}
    }
}
*///?}
