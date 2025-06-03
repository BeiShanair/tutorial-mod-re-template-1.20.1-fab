package com.besson.tutorial.block.custom;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.ModFluid;
import com.besson.tutorial.block.ModFluids;
import com.besson.tutorial.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;

public abstract class OilFluid extends ModFluid {
    // 获取流动的流体
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_OIL;
    }

    // 获取静止的流体
    @Override
    public Fluid getStill() {
        return ModFluids.OIL;
    }

    // 获取流体对应的桶装流体
    @Override
    public Item getBucketItem() {
        return ModItems.OIL_BUCKET;
    }

    // 流体的方块状态
    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.OIL.getDefaultState().with(FluidBlock.LEVEL, WaterFluid.getBlockStateLevel(state));
    }

    public static class Flowing extends OilFluid{
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }

    public static class Still extends OilFluid{

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
}
