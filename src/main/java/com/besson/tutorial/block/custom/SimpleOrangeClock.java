package com.besson.tutorial.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SimpleOrangeClock extends Block {
    // 此类代码来自ArknightsFurniture

    // 要定义方块朝向要引入一个DirectionProperty变量 FACING（这里用HORIZONTAL_FACING，因为上下两个还不需要用）
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    // 定义方块的碰撞箱
    private static final VoxelShape SHAPE_N = Block.createCuboidShape(3, 3, 15, 13, 13, 16);
    private static final VoxelShape SHAPE_W = Block.createCuboidShape(15, 3, 3, 16, 13, 13);
    private static final VoxelShape SHAPE_S = Block.createCuboidShape(3, 3, 0, 13, 13, 1);
    private static final VoxelShape SHAPE_E = Block.createCuboidShape(0, 3, 3, 1, 13, 13);

    public SimpleOrangeClock(Settings settings) {
        super(settings);
        // 设置方块的默认朝向
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    // 重写getOutlineShape方法，返回不同面向的方块碰撞箱
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case WEST -> SHAPE_W;
            case SOUTH -> SHAPE_S;
            case EAST -> SHAPE_E;
            default -> SHAPE_N;
        };
    }

    // 重写appendProperties方法，添加FACING属性
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    // 重写rotate方法，获取方块旋转后的状态
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    // 重写mirror方法，获取方块镜像后的状态
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    // 重写getPlacementState方法，获取方块放置后的状态（取玩家朝向的反方向）
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
}
