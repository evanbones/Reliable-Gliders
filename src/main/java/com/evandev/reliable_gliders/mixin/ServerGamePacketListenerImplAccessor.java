package com.evandev.reliable_gliders.mixin;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerGamePacketListenerImpl.class)
public interface ServerGamePacketListenerImplAccessor {
    @Accessor("aboveGroundTickCount")
    void reliableGliders$setAboveGroundTickCount(int count);

    @Accessor("aboveGroundVehicleTickCount")
    void reliableGliders$setAboveGroundVehicleTickCount(int count);
}
