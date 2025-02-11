package com.besson.tutorial.item.custom;

import com.besson.tutorial.sound.ModSoundEvents;
import com.besson.tutorial.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PickaxeAxe extends AxeItem {

    public PickaxeAxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE_MINEABLE) ? this.miningSpeed : 1.0F;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE_MINEABLE);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip.tutorial.pickaxe_axe.shift"));
        } else {
            tooltip.add(Text.translatable("tooltip.tutorial.pickaxe_axe"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            BlockState state = context.getWorld().getBlockState(context.getBlockPos());
            if (state.isIn(ModBlockTags.PICKAXE_AXE_MINEABLE)) {
                context.getWorld().playSound(
                        null, context.getBlockPos(), ModSoundEvents.TEST, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
        return ActionResult.SUCCESS;
    }
}
