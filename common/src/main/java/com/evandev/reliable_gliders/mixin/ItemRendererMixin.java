package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.registry.ModItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private BakedModel reliableGliders$guiModel(BakedModel originalModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ItemDisplayContext displayContext) {
        if (stack.is(ModItems.GLIDER) && displayContext == ItemDisplayContext.GUI) {
            return ((ItemRenderer) (Object) this).getItemModelShaper().getItemModel(stack.getItem());
        }
        return originalModel;
    }
}