package com.evandev.reliable_gliders.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

//? if >=26.1 {
/*import com.evandev.reliable_gliders.Constants;
*///?}

public class ClientConstants {
    //? if >=26.1 {
    /*public static final KeyMapping.Category RELIABLE_GLIDERS_CATEGORY = KeyMapping.Category.register(Constants.id("main"));
    *///?} else {
    public static final String RELIABLE_GLIDERS_CATEGORY = "key.categories.reliable_gliders";
    //?}

    public static KeyMapping DEPLOY_KEY = new KeyMapping(
            "key.reliable_gliders.deploy",
            //? if >=26.3 {
            /*InputConstants.Type.KEYBOARD,
            *///?} else {
            InputConstants.Type.KEYSYM,
            //?}
            InputConstants.UNKNOWN.getValue(),
            RELIABLE_GLIDERS_CATEGORY
    );
}
