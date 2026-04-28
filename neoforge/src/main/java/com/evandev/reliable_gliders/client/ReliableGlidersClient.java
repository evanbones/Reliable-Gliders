package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.platform.Services;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ReliableGlidersClient {
    public static void register(ModContainer container, IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ClothConfigIntegration.createScreen(parent));
        }

        modEventBus.addListener(ReliableGlidersClient::onModelRegister);
        modEventBus.addListener(ReliableGlidersClient::onClientSetup);
    }

    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/glider_3d")));
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.GLIDER, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gliding"),
                    (stack, level, entity, seed) -> {
                        if (entity instanceof Player player) {
                            boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
                            if (isHolding && !player.onGround() && !player.isFallFlying() && player.getDeltaMovement().y < 0) {
                                return 1.0F;
                            }
                        }
                        return 0.0F;
                    });
        });
    }
}