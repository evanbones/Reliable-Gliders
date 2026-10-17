package com.evandev.reliable_gliders.network;

import com.evandev.reliable_gliders.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

//? if >=1.21 {
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
//?}

//? if >=1.21 {
public record SyncGliderSettingsPayload(boolean isKeyBound) implements CustomPacketPayload {
    public static final Type<SyncGliderSettingsPayload> TYPE = new Type<>(Constants.id("sync_settings"));
    public static final StreamCodec<FriendlyByteBuf, SyncGliderSettingsPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SyncGliderSettingsPayload::isKeyBound,
            SyncGliderSettingsPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
//?} else {
/*public record SyncGliderSettingsPayload(boolean isKeyBound) {
*///?}
    public static final ResourceLocation ID = Constants.id("sync_settings");

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isKeyBound);
    }

    public static SyncGliderSettingsPayload read(FriendlyByteBuf buf) {
        return new SyncGliderSettingsPayload(buf.readBoolean());
    }
}
