package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;

public class ClientConstants {
    public static final KeyMapping.Category RELIABLE_GLIDERS_CATEGORY = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "main"));

    public static KeyMapping DEPLOY_KEY = new KeyMapping(
            "key.reliable_gliders.deploy",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            RELIABLE_GLIDERS_CATEGORY
    );
}