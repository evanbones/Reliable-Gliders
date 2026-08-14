package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {

    @WrapOperation(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack reliableGliders$injectMainHand(LivingEntity instance, Operation<ItemStack> original) {
        ItemStack stack = original.call(instance);
        if (instance instanceof Player player && GlidingState.isGliding(player)) {
            boolean holdingGlider = stack.is(ModItems.GLIDER) || player.getOffhandItem().is(ModItems.GLIDER);

            if (!holdingGlider) {
                ItemStack gliderStack = GlidingState.getGliderStack(player);
                return gliderStack.isEmpty() ? new ItemStack(ModItems.GLIDER) : gliderStack;
            }
        }
        return stack;
    }

    @WrapOperation(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack reliableGliders$injectOffHand(LivingEntity instance, Operation<ItemStack> original) {
        ItemStack stack = original.call(instance);
        if (instance instanceof Player player && GlidingState.isGliding(player)) {
            boolean holdingGlider = player.getMainHandItem().is(ModItems.GLIDER) || stack.is(ModItems.GLIDER);

            if (!holdingGlider) {
                return ItemStack.EMPTY;
            }
        }
        return stack;
    }
}