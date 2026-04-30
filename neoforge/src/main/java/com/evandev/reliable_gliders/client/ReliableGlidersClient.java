package com.evandev.reliable_gliders.client;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.client.integration.ClothConfigIntegration;
import com.evandev.reliable_gliders.platform.Services;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelIdentifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ReliableGlidersClient {
    public static void register(ModContainer container, IEventBus modEventBus) {
        if (Services.PLATFORM.isModLoaded("cloth_config")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ClothConfigIntegration.createScreen(parent));
        }

        modEventBus.addListener(ReliableGlidersClient::onModelRegister);
        modEventBus.addListener(ReliableGlidersClient::onClientSetup);
        modEventBus.addListener(ReliableGlidersClient::onItemColors);
    }

    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        event.register(ModelIdentifier.standalone(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "item/glider_3d")));
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.GLIDER, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gliding"),
                    (stack, level, entity, seed) -> {
                        if (entity instanceof Player player && GlidingState.isGliding(player)) {
                            return 1.0F;
                        }
                        return 0.0F;
                    });
        });
    }

    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 0) {
                DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
                return color != null ? (0xFF000000 | color.rgb()) : -1;
            }
            return -1;
        }, ModItems.GLIDER);
    }
}