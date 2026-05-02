package com.evandev.reliable_gliders.item;

import com.evandev.reliable_gliders.api.GlidingState;
import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                return true;
            } else if (!state.isAir() && state.canOcclude()) {
                break;
            }
        }
        return false;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
        if (!(entity instanceof Player player)) return;

        boolean isMainHand = slot == EquipmentSlot.MAINHAND;
        boolean isOffHand = slot == EquipmentSlot.OFFHAND;
        boolean isChest = ModConfig.get().equipToChestplate && slot == EquipmentSlot.CHEST;

        if (!isMainHand && !isOffHand && !isChest && slot != null) return;

        if (GlidingState.isGliding(player)) {
            if (player instanceof ServerPlayer serverPlayer && level.getGameTime() % 20 == 0) {
                stack.hurtAndBreak(1, serverPlayer, slot != null ? slot : EquipmentSlot.MAINHAND);
            }
        }
    }
}