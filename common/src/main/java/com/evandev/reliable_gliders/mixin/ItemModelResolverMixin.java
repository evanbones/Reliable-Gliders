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
            ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext,
            @Nullable Level level, @Nullable ItemOwner owner, int seed, CallbackInfo ci
    ) {
        if (owner instanceof Player player && GlidingState.isGliding(player)) {
            boolean isHand = displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND ||
                    displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND ||
                    displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ||
                    displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;

            if (isHand) {
                ci.cancel();

                ItemStack gliderStack = GlidingState.getGliderStack(player);
                if (gliderStack.isEmpty()) {
                    gliderStack = new ItemStack(ModItems.GLIDER);
                }

                Identifier modelId = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "glider_active");
                ClientLevel clientLevel = level instanceof ClientLevel cl ? cl : null;
                Minecraft.getInstance().getModelManager().getItemModel(modelId).update(
                        output, gliderStack, (ItemModelResolver) (Object) this, displayContext, clientLevel, owner, seed
                );
            }
        }
    }
}