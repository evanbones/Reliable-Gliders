package com.evandev.reliable_gliders.item;

import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class GliderItem extends Item implements Equipable {
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
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return ModConfig.get().equipToChestplate ? EquipmentSlot.CHEST : EquipmentSlot.MAINHAND;
    }

    @Override
    public @NotNull Holder<SoundEvent> getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (ModConfig.get().equipToChestplate) {
            return this.swapWithEquipmentSlot(this, level, player, hand);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}