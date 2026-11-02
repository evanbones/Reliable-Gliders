package com.evandev.reliable_gliders.item;

import com.evandev.reliable_gliders.config.ModConfig;
import com.evandev.reliable_gliders.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

//? if <26.1 {
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
//?}
//? if >=1.21 && <26.1 {
import net.minecraft.core.Holder;
//?}
//? if <1.21 {
/*import net.minecraft.world.item.DyeableLeatherItem;
*///?}

//? if <1.21 {
/*public class GliderItem extends Item implements Equipable, DyeableLeatherItem {
*///?} else if <26.1 {
public class GliderItem extends Item implements Equipable {
//?} else {
/*public class GliderItem extends Item {
*///?}
    public GliderItem(Properties properties) {
        super(properties);
    }

    public static boolean hasUpdraft(Player player) {
        Level level = player.level();
        //? if >=26.1 {
        /*String dimension = level.dimension().identifier().toString();
        *///?} else {
        String dimension = level.dimension().location().toString();
        //?}
        if (ModConfig.get().updraftDisabledDimensions.contains(dimension)) {
            return false;
        }

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

    /**
     * Returns the dyed color of the glider, or -1 if it is undyed.
     */
    public static int getDyeColor(ItemStack stack) {
        //? if <1.21 {
        /*return stack.getItem() instanceof DyeableLeatherItem dyeable && dyeable.hasCustomColor(stack) ? dyeable.getColor(stack) : -1;
        *///?} else {
        net.minecraft.world.item.component.DyedItemColor color = stack.get(net.minecraft.core.component.DataComponents.DYED_COLOR);
        return color != null ? color.rgb() : -1;
        //?}
    }

    //? if <26.1 {
    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
        return repairCandidate.is(ModTags.Items.GLIDER_REPAIR_ITEMS);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return ModConfig.get().equipToChestplate ? EquipmentSlot.CHEST : EquipmentSlot.MAINHAND;
    }

    //? if >=1.21 {
    @Override
    public @NotNull Holder<SoundEvent> getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
    //?} else {
    /*@Override
    public @NotNull SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
    *///?}

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (ModConfig.get().equipToChestplate) {
            return this.swapWithEquipmentSlot(this, level, player, hand);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
    //?}

    // Durability loss would otherwise replay the equip animation every second while gliding.
    //? if fabric && >=1.21 {
    /*@Override
    public boolean allowComponentsUpdateAnimation(@NotNull Player player, @NotNull net.minecraft.world.InteractionHand hand, @NotNull ItemStack oldStack, @NotNull ItemStack newStack) {
        return false;
    }
    *///?} else if fabric {
    /*@Override
    public boolean allowNbtUpdateAnimation(@NotNull Player player, @NotNull net.minecraft.world.InteractionHand hand, @NotNull ItemStack oldStack, @NotNull ItemStack newStack) {
        return false;
    }
    *///?} else {
    @Override
    public boolean shouldCauseReequipAnimation(@NotNull ItemStack oldStack, @NotNull ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }
    //?}
}
