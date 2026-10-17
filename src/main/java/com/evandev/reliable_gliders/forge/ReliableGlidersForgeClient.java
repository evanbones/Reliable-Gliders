package com.evandev.reliable_gliders.forge;

//? if forge {
/*import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.client.ClientConstants;
import com.evandev.reliable_gliders.client.GliderClient;
import com.evandev.reliable_gliders.client.integration.YACLIntegration;
import com.evandev.reliable_gliders.platform.Services;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ReliableGlidersForgeClient {

    public static void register(IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> YACLIntegration.createScreen(parent))
            );
        }

        modEventBus.addListener(ReliableGlidersForgeClient::registerKeyMappings);
        modEventBus.addListener(ReliableGlidersForgeClient::onModelRegister);
        modEventBus.addListener(ReliableGlidersForgeClient::onClientSetup);
        modEventBus.addListener(ReliableGlidersForgeClient::onItemColors);

        MinecraftForge.EVENT_BUS.addListener(ReliableGlidersForgeClient::onClientTick);
    }

    private static void onModelRegister(ModelEvent.RegisterAdditional event) {
        event.register(Constants.id("item/glider_3d"));
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(ModItems.GLIDER, Constants.id("gliding"),
                (stack, level, entity, seed) -> entity instanceof Player player && GlidingState.isGliding(player) ? 1.0F : 0.0F));
    }

    private static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(GliderClient::getGliderTint, ModItems.GLIDER);
    }

    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ClientConstants.DEPLOY_KEY);
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            GliderClient.onClientTick(Minecraft.getInstance());
        }
    }
}
*///?}
