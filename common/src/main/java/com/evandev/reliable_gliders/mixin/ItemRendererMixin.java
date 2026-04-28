package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.item.GliderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private BakedModel reliable_gliders$swapGliderModel(BakedModel originalModel, ItemStack stack, ItemDisplayContext displayContext) {
        if (stack.getItem() instanceof GliderItem && displayContext != ItemDisplayContext.GUI && displayContext != ItemDisplayContext.GROUND && displayContext != ItemDisplayContext.FIXED) {

            ModelResourceLocation modelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/glider_3d"), "standalone");
            BakedModel model3d = Minecraft.getInstance().getModelManager().getModel(modelLocation);

            if (model3d != Minecraft.getInstance().getModelManager().getMissingModel()) {
                return model3d;
            }
        }
        return originalModel;
    }
}