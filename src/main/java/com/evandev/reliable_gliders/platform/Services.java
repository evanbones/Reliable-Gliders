package com.evandev.reliable_gliders.platform;

//? if fabric && <26.1 {
/*import com.evandev.reliable_gliders.compat.TrinketsCompat;
*///?}
//? if >=26.1 {
/*import com.evandev.reliable_gliders.compat.TrinketsUpdatedCompat;
*///?}
//? if forgelike {
import com.evandev.reliable_gliders.compat.CuriosCompat;
//?}
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

//? if fabric {
/*import net.fabricmc.loader.api.FabricLoader;
*///?}
//? if forge {
/*import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
*///?}
//? if neoforge {
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
//?}

import java.nio.file.Path;

public class Services {
    public static final Services PLATFORM = new Services();

    public boolean isModLoaded(String modId) {
        //? if fabric {
        /*return FabricLoader.getInstance().isModLoaded(modId);
        *///?} else {
        return ModList.get().isLoaded(modId);
        //?}
    }

    public Path getConfigDirectory() {
        //? if fabric {
        /*return FabricLoader.getInstance().getConfigDir();
        *///?} else {
        return FMLPaths.CONFIGDIR.get();
        //?}
    }

    public boolean isGliderEquippedInAccessorySlot(Player player) {
        return !getGliderStackInAccessorySlot(player).isEmpty();
    }

    public ItemStack getGliderStackInAccessorySlot(Player player) {
        //? if fabric && <26.1 {
        /*if (isModLoaded("trinkets")) {
            ItemStack stack = TrinketsCompat.getGlider(player);
            if (!stack.isEmpty()) return stack;
        }
        *///?}
        //? if >=26.1 {
        /*if (isModLoaded(TrinketsUpdatedCompat.MOD_ID)) {
            ItemStack stack = TrinketsUpdatedCompat.getGlider(player);
            if (!stack.isEmpty()) return stack;
        }
        *///?}
        //? if forgelike {
        if (isModLoaded("curios")) {
            ItemStack stack = CuriosCompat.getGlider(player);
            if (!stack.isEmpty()) return stack;
        }
        //?}
        return ItemStack.EMPTY;
    }

    public void damageGliderInAccessorySlot(ServerPlayer player) {
        //? if fabric && <26.1 {
        /*if (isModLoaded("trinkets")) {
            TrinketsCompat.damageGlider(player);
        }
        *///?}
        //? if >=26.1 {
        /*if (isModLoaded(TrinketsUpdatedCompat.MOD_ID)) {
            TrinketsUpdatedCompat.damageGlider(player);
        }
        *///?}
        //? if forgelike {
        if (isModLoaded("curios")) {
            CuriosCompat.damageGlider(player);
        }
        //?}
    }
}
