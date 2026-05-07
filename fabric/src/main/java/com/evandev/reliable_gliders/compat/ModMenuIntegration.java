package com.evandev.reliable_gliders.compat;

import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.platform.Services;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (Services.PLATFORM.isModLoaded("cloth-config")) {
            return ClothConfigIntegration::createScreen;
        }

        return parent -> null;
    }
}