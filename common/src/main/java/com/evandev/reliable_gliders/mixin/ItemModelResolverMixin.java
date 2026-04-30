package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {

    @Inject(method = "appendItemLayers", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$overrideGliderModel(
            ItemStackRenderState output,
            ItemStack item,
            ItemDisplayContext displayContext,
            @Nullable Level level,
            @Nullable ItemOwner owner,
            int seed,
            CallbackInfo ci
    ) {
        if (item.is(ModItems.GLIDER)) {
            if (owner instanceof Player player && GlidingState.isGliding(player)) {
                if (displayContext != ItemDisplayContext.GUI && displayContext != ItemDisplayContext.FIXED) {

                    ci.cancel();

                    Identifier modelId = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "glider_active");
                    ClientLevel clientLevel = level instanceof ClientLevel cl ? cl : null;

                    Minecraft.getInstance().getModelManager().getItemModel(modelId).update(
                            output, item, (ItemModelResolver) (Object) this, displayContext, clientLevel, owner, seed
                    );
                }
            }
        }
    }
}