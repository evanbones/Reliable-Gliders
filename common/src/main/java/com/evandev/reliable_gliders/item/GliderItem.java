package com.evandev.reliable_gliders.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GliderItem extends Item {
    public GliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!(entity instanceof Player player)) return;

        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;

        if (isHolding && !player.onGround() && !player.isFallFlying() && player.getDeltaMovement().y < 0) {

            if (player.fallDistance > 0.0F) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 0.85f);
            }

            player.fallDistance = 0.0F;

            boolean hasUpdraft = false;
            for (int i = 1; i <= 15; i++) {
                BlockPos checkPos = player.blockPosition().below(i);
                BlockState state = level.getBlockState(checkPos);

                // TODO: less hardcoded checks
                if (state.is(BlockTags.FIRE) || state.is(BlockTags.CAMPFIRES) || state.is(Blocks.LAVA)) {
                    hasUpdraft = true;
                    break;
                } else if (!state.isAir() && state.canOcclude()) {
                    break;
                }
            }

            if (hasUpdraft) {
                player.setDeltaMovement(player.getDeltaMovement().x, 0.4, player.getDeltaMovement().z);
            } else {
                player.setDeltaMovement(player.getDeltaMovement().x, Math.max(player.getDeltaMovement().y, -0.15), player.getDeltaMovement().z);
            }

            if (!level.isClientSide() && level.getGameTime() % 20 == 0) {
                stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
            }
        }
    }
}