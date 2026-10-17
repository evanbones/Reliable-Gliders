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
public record SetGliderStatePayload(boolean isGliding) implements CustomPacketPayload {
    public static final Type<SetGliderStatePayload> TYPE = new Type<>(Constants.id("set_glider_state"));
    public static final StreamCodec<FriendlyByteBuf, SetGliderStatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SetGliderStatePayload::isGliding,
            SetGliderStatePayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
//?} else {
/*public record SetGliderStatePayload(boolean isGliding) {
*///?}
    public static final ResourceLocation ID = Constants.id("set_glider_state");

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isGliding);
    }

    public static SetGliderStatePayload read(FriendlyByteBuf buf) {
        return new SetGliderStatePayload(buf.readBoolean());
    }
}
