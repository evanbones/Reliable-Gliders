package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.api.GliderStateAccess;
import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("RETURN"))
    private void reliableGliders$extractGliding(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Player player) {
            ((GliderStateAccess) state).reliableGliders$setGliding(GlidingState.isGliding(player));

            if (GlidingState.isGliding(player)) {
                boolean holdingGlider = player.getMainHandItem().is(ModItems.GLIDER) || player.getOffhandItem().is(ModItems.GLIDER);

                if (!holdingGlider) {
                    ItemStack gliderStack = GlidingState.getGliderStack(player);
                    if (gliderStack.isEmpty()) {
                        gliderStack = new ItemStack(ModItems.GLIDER);
                    }

                    Identifier modelId = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "glider_active");
                    Minecraft.getInstance().getModelManager().getItemModel(modelId).update(
                            state.rightHandItemState,
                            gliderStack,
                            Minecraft.getInstance().getItemModelResolver(),
                            ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                            player.level() instanceof ClientLevel cl ? cl : null,
                            player,
                            0
                    );
                }
            }
        }
    }
}