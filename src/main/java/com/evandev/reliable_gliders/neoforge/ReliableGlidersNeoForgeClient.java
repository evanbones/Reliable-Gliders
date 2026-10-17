package com.evandev.reliable_gliders.neoforge;

//? if neoforge {
import com.evandev.reliable_gliders.client.ClientConstants;
import com.evandev.reliable_gliders.client.GliderClient;
import com.evandev.reliable_gliders.client.integration.YACLIntegration;
import com.evandev.reliable_gliders.platform.Services;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

//? if <26.1 {
import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
//?}

public class ReliableGlidersNeoForgeClient {

    public static void register(ModContainer container, IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("yet_another_config_lib_v3")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> YACLIntegration.createScreen(parent));
        }

        modEventBus.addListener(ReliableGlidersNeoForgeClient::registerKeyMappings);
        //? if <26.1 {
        modEventBus.addListener(ReliableGlidersNeoForgeClient::onModelRegister);
        modEventBus.addListener(ReliableGlidersNeoForgeClient::onClientSetup);
        modEventBus.addListener(ReliableGlidersNeoForgeClient::onItemColors);
        //?}

        NeoForge.EVENT_BUS.addListener(ReliableGlidersNeoForgeClient::onClientTick);
    }

    //? if <26.1 {
    private static void onModelRegister(ModelEvent.RegisterAdditional event) {
        event.register(ModelResourceLocation.standalone(Constants.id("item/glider_3d")));
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(ModItems.GLIDER, Constants.id("gliding"),
                (stack, level, entity, seed) -> entity instanceof Player player && GlidingState.isGliding(player) ? 1.0F : 0.0F));
    }

    private static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(GliderClient::getGliderTint, ModItems.GLIDER);
    }
    //?}

    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ClientConstants.DEPLOY_KEY);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        GliderClient.onClientTick(Minecraft.getInstance());
    }
}
//?}
