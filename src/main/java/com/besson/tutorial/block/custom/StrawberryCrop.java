package com.besson.tutorial.block.custom;

import com.besson.tutorial.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class StrawberryCrop extends CropBlock {
    /* 要创建自定义生长周期的作物，我们需要创建一个继承自CropBlock的类
       CropBlock是Minecraft原版提供的作物类，我们可以通过重写其方法来改变作物的生长周期
     */

    // 定义作物最大生长阶段（0-5）
    public static final int MAX_AGE = 5;

    // 定义作物生长阶段的属性（用于方块状态），在原版的Properties中有的话就可以直接引用
    public static final IntProperty AGE = Properties.AGE_5;

    public StrawberryCrop(Settings settings) {
        super(settings);
    }

    // 重写getMaxAge方法，返回作物最大生长阶段
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    // 重写getAgeProperty方法，返回作物生长阶段的属性
    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    // 重写getAge方法，返回作物当前生长阶段
    @Override
    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    // 将作物生长阶段的属性添加到方块状态中（其实不加也可以，这个会在后面的系列中重点讲解）
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    // 重写getSeedsItem方法，返回作物种子
    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.STRAWBERRY_SEEDS;
    }

    // 重写canPlantOnTop方法，返回作物是否可以种植在某个方块上（例如我们可以让作物种植在泥土上，而不局限于耕地）
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
    }
}
