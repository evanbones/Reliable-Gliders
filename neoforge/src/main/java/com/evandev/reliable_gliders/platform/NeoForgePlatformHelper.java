package com.evandev.reliable_gliders.platform;

import com.evandev.reliable_gliders.content.NeoForgeGliderItem;
import com.evandev.reliable_gliders.item.GliderItem;
import com.evandev.reliable_gliders.platform.services.IPlatformHelper;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getCurrent().getDist() == Dist.CLIENT;
    }

    @Override
    public GliderItem createGliderItem(Item.Properties properties) {
        return new NeoForgeGliderItem(properties);
    }
}