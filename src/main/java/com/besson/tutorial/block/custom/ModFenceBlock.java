package com.besson.tutorial.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ModFenceBlock extends Block {
    // 原版的FenceBlock,HorizontalConnectingBlock,ConnectingBlock都是值得研究的连接型方块的案例

    // 东西南北的布尔值属性
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;

    public ModFenceBlock(Settings settings) {
        super(settings);
        // 设置默认状态
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false));
    }

    // 添加方块状态
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    // 重写getStateForNeighborUpdate
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockState northState = world.getBlockState(north);
        BlockState eastState = world.getBlockState(east);
        BlockState southState = world.getBlockState(south);
        BlockState westState = world.getBlockState(west);

        return this.getDefaultState()
                .with(NORTH, northState.isOf(this))
                .with(EAST, eastState.isOf(this))
                .with(SOUTH, southState.isOf(this))
                .with(WEST, westState.isOf(this));
    }
}
