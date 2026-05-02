package com.evandev.reliable_gliders.platform;

import com.evandev.reliable_gliders.content.NeoForgeGliderItem;
import com.evandev.reliable_gliders.item.GliderItem;
import com.evandev.reliable_gliders.platform.services.IPlatformHelper;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.CuriosApi;

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

    @Override
    public boolean isGliderEquippedInAccessorySlot(Player player) {
        if (isModLoaded("curios")) {
            return CuriosApi.getCuriosInventory(player)
                    .map(inv -> inv.isEquipped(ModItems.GLIDER))
                    .orElse(false);
        }
        return false;
    }
}