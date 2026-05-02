package com.evandev.reliable_gliders.platform;

import com.evandev.reliable_gliders.content.FabricGliderItem;
import com.evandev.reliable_gliders.item.GliderItem;
import com.evandev.reliable_gliders.platform.services.IPlatformHelper;
import com.evandev.reliable_gliders.registry.ModItems;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public GliderItem createGliderItem(Item.Properties properties) {
        return new FabricGliderItem(properties);
    }

    @Override
    public boolean isGliderEquippedInAccessorySlot(Player player) {
        if (isModLoaded("trinkets")) {
            return TrinketsApi.getTrinketComponent(player)
                    .map(component -> component.isEquipped(ModItems.GLIDER))
                    .orElse(false);
        }
        return false;
    }
}