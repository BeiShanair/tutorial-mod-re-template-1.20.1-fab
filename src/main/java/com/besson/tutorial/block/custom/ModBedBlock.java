package com.besson.tutorial.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ModBedBlock extends BedBlock {

    // 方块的碰撞箱
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);

    public ModBedBlock(DyeColor color, Settings settings) {
        super(color, settings);
    }

    // 设置各个部分的渲染设置
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (state.get(PART) == BedPart.HEAD) {
            return BlockRenderType.MODEL;
        } else {
            return BlockRenderType.INVISIBLE;
        }
    }

    // 返回碰撞箱
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
