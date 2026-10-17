package com.evandev.reliable_gliders.fabric;

//? if fabric {
/*import com.evandev.reliable_gliders.client.ClientConstants;
import com.evandev.reliable_gliders.client.GliderClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

//? if >=26.1 {
/^import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
^///?} else {
import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.entity.player.Player;
//?}
//? if >=1.21 && <26.1 {
import net.minecraft.client.renderer.item.ItemProperties;
//?} else if <1.21 {
/^import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
^///?}

public class ReliableGlidersFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //? if >=26.1 {
        /^KeyMappingHelper.registerKeyMapping(ClientConstants.DEPLOY_KEY);
        ^///?} else {
        KeyBindingHelper.registerKeyBinding(ClientConstants.DEPLOY_KEY);

        ModelLoadingPlugin.register(pluginContext -> pluginContext.addModels(Constants.id("item/glider_3d")));

        //? if >=1.21 {
        ItemProperties.register(ModItems.GLIDER, Constants.id("gliding"),
        //?} else {
        /^FabricModelPredicateProviderRegistry.register(ModItems.GLIDER, Constants.id("gliding"),
        ^///?}
                (stack, level, entity, seed) -> entity instanceof Player player && GlidingState.isGliding(player) ? 1.0F : 0.0F);

        ColorProviderRegistry.ITEM.register(GliderClient::getGliderTint, ModItems.GLIDER);
        //?}

        ClientTickEvents.END_CLIENT_TICK.register(GliderClient::onClientTick);
    }
}
*///?}
