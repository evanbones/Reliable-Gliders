package com.evandev.reliable_gliders.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ClientConstants {
    public static final String RELIABLE_GLIDERS_CATEGORY = "key.categories.reliable_gliders";

    public static KeyMapping DEPLOY_KEY = new KeyMapping(
            "key.reliable_gliders.deploy",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            RELIABLE_GLIDERS_CATEGORY
    );
}