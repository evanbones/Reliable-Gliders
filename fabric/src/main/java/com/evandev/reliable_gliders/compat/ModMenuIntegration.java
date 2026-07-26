package com.evandev.reliable_gliders.compat;

import com.evandev.reliable_gliders.client.integration.YACLIntegration;
import com.evandev.reliable_gliders.platform.Services;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (Services.PLATFORM.isModLoaded("yet_another_config_lib_v3")) {
            return YACLIntegration::createScreen;
        }

        return parent -> null;
    }
}