package com.evandev.reliable_gliders.api;

public interface GliderStateAccess {
    boolean reliableGliders$isGliding();

    void reliableGliders$setGliding(boolean gliding);
}