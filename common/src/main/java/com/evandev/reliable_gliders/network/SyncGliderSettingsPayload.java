package com.evandev.reliable_gliders.network;

import com.evandev.reliable_gliders.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record SyncGliderSettingsPayload(boolean isKeyBound) implements CustomPacketPayload {
    public static final Type<SyncGliderSettingsPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "sync_settings"));
    public static final StreamCodec<FriendlyByteBuf, SyncGliderSettingsPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SyncGliderSettingsPayload::isKeyBound,
            SyncGliderSettingsPayload::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}