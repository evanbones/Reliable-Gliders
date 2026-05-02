package com.evandev.reliable_gliders.network;

import com.evandev.reliable_gliders.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetGliderStatePayload(boolean isGliding) implements CustomPacketPayload {
    public static final Type<SetGliderStatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "set_glider_state"));
    public static final StreamCodec<FriendlyByteBuf, SetGliderStatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SetGliderStatePayload::isGliding,
            SetGliderStatePayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}