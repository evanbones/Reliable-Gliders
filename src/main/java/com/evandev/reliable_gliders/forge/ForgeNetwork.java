package com.evandev.reliable_gliders.forge;

//? if forge {
/*import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.network.SetGliderStatePayload;
import com.evandev.reliable_gliders.network.SyncGliderSettingsPayload;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ForgeNetwork {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            Constants.id("main"),
            () -> PROTOCOL_VERSION,
            NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION),
            NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION)
    );

    public static void register() {
        CHANNEL.messageBuilder(SetGliderStatePayload.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SetGliderStatePayload::write)
                .decoder(SetGliderStatePayload::read)
                .consumerMainThread((payload, context) -> {
                    ServerPlayer player = context.get().getSender();
                    if (player != null) {
                        GlidingState.setGliding(player, payload.isGliding());
                    }
                })
                .add();

        CHANNEL.messageBuilder(SyncGliderSettingsPayload.class, 1, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SyncGliderSettingsPayload::write)
                .decoder(SyncGliderSettingsPayload::read)
                .consumerMainThread((payload, context) -> {
                    ServerPlayer player = context.get().getSender();
                    if (player != null) {
                        GlidingState.setKeyBound(player, payload.isKeyBound());
                    }
                })
                .add();
    }

    public static void sendToServer(Connection connection, Object payload) {
        if (CHANNEL.isRemotePresent(connection)) {
            CHANNEL.sendToServer(payload);
        }
    }
}
*///?}
