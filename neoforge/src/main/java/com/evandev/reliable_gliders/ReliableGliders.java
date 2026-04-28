package com.evandev.reliable_gliders;

import com.evandev.reliable_gliders.client.ReliableGlidersClient;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class ReliableGliders {
    public ReliableGliders(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::buildContents);
        modEventBus.addListener(this::onRegister);

        if (FMLEnvironment.dist.isClient()) {
            ReliableGlidersClient.register(modContainer, modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CommonClass.init();
    }

    private void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            ModItems.init();
        }
    }

    private void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.GLIDER);
        }
    }
}