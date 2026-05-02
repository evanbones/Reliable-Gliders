package com.evandev.reliable_gliders.item;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class GliderItem extends Item {
    public GliderItem(Properties properties) {
        super(properties);
    }

    public static boolean hasUpdraft(Player player) {
        Level level = player.level();
        int maxHeight = ModConfig.get().updraftHeight;

        for (int i = 1; i <= maxHeight; i++) {
            BlockPos checkPos = player.blockPosition().below(i);
            BlockState state = level.getBlockState(checkPos);

            if (state.is(ModTags.Blocks.UPDRAFT_BLOCKS)) {
                if (state.hasProperty(BlockStateProperties.LIT)) {
                    if (state.getValue(BlockStateProperties.LIT)) {
                        return true;
                    }
                } else {
                    return true;
                }
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

        if (!isMainHand && !isOffHand && !isChest && slot != null) return;

        boolean isGliding = GlidingState.isGliding(player);
        boolean wasGliding = GlidingState.wasGliding(player);

        if (isGliding) {
            player.fallDistance = 0.0F;
            if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.aboveGroundTickCount = 0;
                serverPlayer.connection.aboveGroundVehicleTickCount = 0;
            }

            if (!wasGliding) {
                level.playSound(player, player.blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 0.85f);
            }

            GlidingState.setGliding(player, true);
            player.fallDistance = 0.0F;

            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.aboveGroundTickCount = 0;
                serverPlayer.connection.aboveGroundVehicleTickCount = 0;
            }

            double currentY = player.getDeltaMovement().y;

            if (hasUpdraft(player)) {
                double newY = Math.max(currentY, ModConfig.get().updraftStrength);
                newY = Mth.lerp(0.2, currentY, newY);

                player.setDeltaMovement(player.getDeltaMovement().x, newY, player.getDeltaMovement().z);
            } else {
                player.setDeltaMovement(player.getDeltaMovement().x, Math.max(currentY, -0.05), player.getDeltaMovement().z);
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