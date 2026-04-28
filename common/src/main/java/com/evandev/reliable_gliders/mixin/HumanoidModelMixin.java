package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GlidingState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void reliableGliders$setupGliderAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player) {

            if (GlidingState.isGliding(player)) {
                HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;

                model.rightArm.xRot = (float) Math.PI;
                model.leftArm.xRot = (float) Math.PI;
                model.rightArm.yRot = 0;
                model.leftArm.yRot = 0;
                model.rightArm.zRot = 0;
                model.leftArm.zRot = 0;
            }
        }
    }
}