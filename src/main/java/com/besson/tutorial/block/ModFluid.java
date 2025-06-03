package com.besson.tutorial.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class ModFluid extends FlowableFluid {
    // 依照Fabric的Wiki编写
    // 这个类继承FlowableFluid，即可以流动的流体

    // 检查流体是否匹配
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getFlowing() || fluid == getStill();
    }

    // 流体是否为无限
    // 不过这个也受游戏规则（GameRule指令）影响
    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    // 在流体破坏方块之前调用的方法
    // 如果是一个可以被流体破坏的方块实体，里面有存储物的话，则掉落里面的东西
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    // 流体是否可以被替换为其他方块（或流体）
    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    // 流体流动速度
    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    // 流体流过每个方块之后降低的高度
    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    // 流体更新频率（每 多少 tick更新一次）
    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    // 流体爆炸抗性
    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }
}
