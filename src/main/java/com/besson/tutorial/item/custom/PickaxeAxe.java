package com.besson.tutorial.item.custom;

import com.besson.tutorial.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
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

    // 重写appendTooltip，添加工具信息
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // 可以用Screen.hasShiftDown()判断shift键是否被按下
        if (Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip.tutorial.pickaxe_axe.shift"));
        } else {
            tooltip.add(Text.translatable("tooltip.tutorial.pickaxe_axe"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
