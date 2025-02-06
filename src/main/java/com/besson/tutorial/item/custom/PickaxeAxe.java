package com.besson.tutorial.item.custom;

import com.besson.tutorial.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class PickaxeAxe extends AxeItem {

    /* 这一次我们来写一个物品
       让它来实现斧和镐的功能
       即它既能像斧头一样挖斧能挖的东西，可以给原木去皮、给生锈的铜块抛光，亦或是去除蜡质，也可以像镐子一样能挖掘矿石
       不过由于斧的逻辑较多，我们可以直接继承AxeItem
     */
    public PickaxeAxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    /* 重写getMiningSpeedMultiplier
       如果方块在tag内，则施加速度加成，否则为1.0
     */
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE_MINEABLE) ? this.miningSpeed : 1.0F;
    }

    /* 重写isSuitableFor
       判断方块是否属于tag内方块（即能否挖掘）
     */

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE_MINEABLE);
    }
}
