package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

//? if >=26.3 {
/*import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?} else {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    //? if >=26.3 {
    /*@Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/component/SwingAnimation;Z)Z", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelSwingAnim(InteractionHand hand, SwingAnimation animation, boolean sendToSwingingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (reliableGliders$shouldCancelSwing(hand)) {
            cir.setReturnValue(false);
        }
    }
    *///?} else {
    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"), cancellable = true)
    private void reliableGliders$cancelSwingAnim(InteractionHand hand, boolean sendToSwingingEntity, CallbackInfo ci) {
        if (reliableGliders$shouldCancelSwing(hand)) {
            ci.cancel();
        }
    }
    //?}

    @Unique
    private boolean reliableGliders$shouldCancelSwing(InteractionHand hand) {
        return (Object) this instanceof Player player
                && GlidingState.isGliding(player)
                && !player.getItemInHand(hand).is(ModTags.Items.GLIDER_USABLE_ITEMS);
    }
}
