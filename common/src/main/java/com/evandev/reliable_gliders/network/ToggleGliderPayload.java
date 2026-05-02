package com.evandev.reliable_gliders.network;

import com.evandev.reliable_gliders.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ToggleGliderPayload() implements CustomPacketPayload {
    public static final Type<ToggleGliderPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "toggle_glider"));
    public static final StreamCodec<FriendlyByteBuf, ToggleGliderPayload> STREAM_CODEC = StreamCodec.unit(new ToggleGliderPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}