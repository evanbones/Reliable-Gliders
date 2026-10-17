package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;

//? if fabric {
/*import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
*///?}
//? if fabric && <1.21 {
/*import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
*///?}
//? if forge {
/*import com.evandev.reliable_gliders.forge.ForgeNetwork;
import net.minecraft.client.Minecraft;
*///?}
//? if neoforge && >=26.1 {
/*import net.neoforged.neoforge.client.network.ClientPacketDistributor;
*///?} else if neoforge {
import net.neoforged.neoforge.network.PacketDistributor;
//?}

public class ClientNetworking {

    public static void sendGliderState(boolean gliding) {
        SetGliderStatePayload payload = new SetGliderStatePayload(gliding);
        //? if fabric && >=1.21 {
        /*if (ClientPlayNetworking.canSend(SetGliderStatePayload.TYPE)) {
            ClientPlayNetworking.send(payload);
        }
        *///?} else if fabric {
        /*if (ClientPlayNetworking.canSend(SetGliderStatePayload.ID)) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            payload.write(buf);
            ClientPlayNetworking.send(SetGliderStatePayload.ID, buf);
        }
        *///?} else if forge {
        /*ForgeNetwork.sendToServer(Minecraft.getInstance().getConnection().getConnection(), payload);
        *///?} else if >=26.1 {
        /*ClientPacketDistributor.sendToServer(payload);
        *///?} else {
        PacketDistributor.sendToServer(payload);
        //?}
    }

    public static void sendKeyBound(boolean keyBound) {
        SyncGliderSettingsPayload payload = new SyncGliderSettingsPayload(keyBound);
        //? if fabric && >=1.21 {
        /*if (ClientPlayNetworking.canSend(SyncGliderSettingsPayload.TYPE)) {
            ClientPlayNetworking.send(payload);
        }
        *///?} else if fabric {
        /*if (ClientPlayNetworking.canSend(SyncGliderSettingsPayload.ID)) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            payload.write(buf);
            ClientPlayNetworking.send(SyncGliderSettingsPayload.ID, buf);
        }
        *///?} else if forge {
        /*ForgeNetwork.sendToServer(Minecraft.getInstance().getConnection().getConnection(), payload);
        *///?} else if >=26.1 {
        /*ClientPacketDistributor.sendToServer(payload);
        *///?} else {
        PacketDistributor.sendToServer(payload);
        //?}
    }
}
