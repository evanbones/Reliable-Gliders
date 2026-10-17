package com.evandev.reliable_gliders.mixin;

import net.minecraft.client.model.HumanoidModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.1 {
/*import com.evandev.reliable_gliders.api.GliderStateAccess;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
*///?} else {
import com.evandev.reliable_gliders.api.GlidingState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
//?}

//? if >=26.1 {
/*@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends HumanoidRenderState> {

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
    private void reliableGliders$setupGliderAnim(T state, CallbackInfo ci) {
        if (((GliderStateAccess) state).reliableGliders$isGliding()) {
            reliableGliders$applyGliderPose(state.ageInTicks);
        }
    }
*///?} else {
@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void reliableGliders$setupGliderAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player && GlidingState.isGliding(player)) {
            reliableGliders$applyGliderPose(ageInTicks);
        }
    }
//?}

    @Unique
    private void reliableGliders$applyGliderPose(float ageInTicks) {
        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;

        // Lock arms
        model.rightArm.xRot = (float) Math.PI;
        model.leftArm.xRot = (float) Math.PI;
        model.rightArm.yRot = 0;
        model.leftArm.yRot = 0;
        model.rightArm.zRot = 0;
        model.leftArm.zRot = 0;

        // Subtle leg sway
        float legSway = (float) Math.sin(ageInTicks * 0.1F) * 0.1F;

        model.rightLeg.xRot = 0.1F + legSway;
        model.leftLeg.xRot = 0.1F - legSway;

        model.rightLeg.yRot = 0;
        model.leftLeg.yRot = 0;
        model.rightLeg.zRot = 0;
        model.leftLeg.zRot = 0;
    }
}
