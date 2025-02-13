package com.besson.tutorial.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class PillarBlock extends Block {
    // 定义一个枚举属性，用于表示柱子的类型
    public static final EnumProperty<Type> TYPE = EnumProperty.of("type2", Type.class);

    public PillarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(TYPE, Type.SINGLE));
    }

    // 添加属性
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    // 根据相邻方块的状态更新当前方块的状态，有了前面的经验相比之下会很简单
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean top = world.getBlockState(pos.up()).isOf(this);
        boolean bottom = world.getBlockState(pos.down()).isOf(this);
        if (top && bottom) {
            return state.with(TYPE, Type.MIDDLE);
        } else if (top) {
            return state.with(TYPE, Type.BOTTOM);
        } else if (bottom) {
            return state.with(TYPE, Type.TOP);
        } else {
            return state.with(TYPE, Type.SINGLE);
        }
    }

    // 嵌套枚举类，罗列柱子的方块状态
    public enum Type implements StringIdentifiable {
        SINGLE("single"),
        TOP("top"),
        BOTTOM("bottom"),
        MIDDLE("middle");

        private final String id;

        Type(String id) {
            this.id = id;
        }

        @Override
        public String asString() {
            return id;
        }
    }
}
