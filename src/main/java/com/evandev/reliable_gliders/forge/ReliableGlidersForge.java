package com.evandev.reliable_gliders.forge;

//? if forge {
/*import com.evandev.reliable_gliders.CommonClass;
import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class ReliableGlidersForge {
    public ReliableGlidersForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::buildContents);
        modEventBus.addListener(this::onRegister);

        if (FMLEnvironment.dist.isClient()) {
            ReliableGlidersForgeClient.register(modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CommonClass.init();
        event.enqueueWork(ForgeNetwork::register);
    }

    private void onRegister(RegisterEvent event) {
        event.register(Registries.ITEM, helper -> ModItems.init(helper::register));
    }

    private void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.GLIDER);
        }
    }
}
*///?}
