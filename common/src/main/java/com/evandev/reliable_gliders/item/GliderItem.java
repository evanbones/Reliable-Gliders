package com.evandev.reliable_gliders.item;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GliderItem extends Item {
    public GliderItem(Properties properties) {
        super(properties);
    }

    public static boolean hasUpdraft(Player player) {
        Level level = player.level();
        for (int i = 1; i <= 15; i++) {
            BlockPos checkPos = player.blockPosition().below(i);
            BlockState state = level.getBlockState(checkPos);

            if (state.is(ModTags.Blocks.UPDRAFT_BLOCKS)) {
                return true;
            } else if (!state.isAir() && state.canOcclude()) {
                break;
            }
        }
        return false;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!(entity instanceof Player player)) return;

        boolean isMainHand = player.getMainHandItem() == stack;
        boolean isOffHand = player.getOffhandItem() == stack;

        if (!isMainHand && !isOffHand) return;
        if (isOffHand && player.getMainHandItem().getItem() instanceof GliderItem) return;

        boolean isGliding = GlidingState.isGliding(player);
        boolean wasGliding = GlidingState.wasGliding(player);

        if (isGliding) {
            if (!wasGliding) {
                level.playSound(player, player.blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 0.85f);
            }

            GlidingState.setGliding(player, true);
            player.fallDistance = 0.0F;

            if (hasUpdraft(player)) {
                player.setDeltaMovement(player.getDeltaMovement().x, ModConfig.get().updraftStrength, player.getDeltaMovement().z);
            } else {
                player.setDeltaMovement(player.getDeltaMovement().x, Math.max(player.getDeltaMovement().y, -0.15), player.getDeltaMovement().z);
            }

            if (!level.isClientSide() && level.getGameTime() % 20 == 0) {
                stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
            }
        } else {
            if (wasGliding) {
                GlidingState.setGliding(player, false);
            }
        }
    }
}