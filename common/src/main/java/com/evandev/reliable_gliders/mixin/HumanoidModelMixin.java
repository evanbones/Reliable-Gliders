package com.evandev.reliable_gliders.mixin;

import com.evandev.reliable_gliders.api.GliderStateAccess;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends HumanoidRenderState> {

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
    private void reliableGliders$setupGliderAnim(T state, CallbackInfo ci) {

        if (((GliderStateAccess) state).reliableGliders$isGliding()) {
            HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;

            // Lock arms
            model.rightArm.xRot = (float) Math.PI;
            model.leftArm.xRot = (float) Math.PI;
            model.rightArm.yRot = 0;
            model.leftArm.yRot = 0;
            model.rightArm.zRot = 0;
            model.leftArm.zRot = 0;

            // Subtle leg sway using state.ageInTicks
            float legSway = (float) Math.sin(state.ageInTicks * 0.1F) * 0.1F;

            model.rightLeg.xRot = 0.1F + legSway;
            model.leftLeg.xRot = 0.1F - legSway;

            model.rightLeg.yRot = 0;
            model.leftLeg.yRot = 0;
            model.rightLeg.zRot = 0;
            model.leftLeg.zRot = 0;
        }
    }
}