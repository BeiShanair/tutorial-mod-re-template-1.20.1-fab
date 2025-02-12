package com.besson.tutorial.block.custom;

import com.besson.tutorial.block.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SofaBlock extends Block {
    // 此类代码来自ArknightsFurniture

    // FACING
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    // TYPE
    public static final EnumProperty<Type> TYPE = EnumProperty.of("type", Type.class);

    public SofaBlock(Settings settings) {
        super(settings);
        // 设置默认状态，沙发面向北，类型为单个
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(TYPE, Type.SINGLE));
    }

    // 重写onUse方法，当玩家右键点击方块时，创建一个座位实体，并让玩家坐在上面
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            return SeatEntity.create(world, pos, 0.25, player, state.get(FACING));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    // 重写getStateForNeighborUpdate，当方块周围的方块发生变化时，更新当前方块的状态
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return this.getRelatedBlockState(state, world, pos, state.get(FACING));
    }

    // 自定义方法getRelatedBlockState，获取方块左右侧是否有相关的方块，并更新Type，从而改变方块状态
    private BlockState getRelatedBlockState(BlockState state, WorldAccess access, BlockPos pos, Direction direction){
        boolean left = isRelatedInDirection(access, pos, direction, true);
        boolean right = isRelatedInDirection(access, pos, direction, false);
        if (left && right){
            return state.with(TYPE, Type.MIDDLE);
        } else if (right) {
            return state.with(TYPE, Type.RIGHT);
        } else if (left) {
            return state.with(TYPE, Type.LEFT);
        }
        return state.with(TYPE, Type.SINGLE);
    }

    // 自定义方法isRelatedInDirection，判断方块左右侧是否有相关的方块
    // 这里其实是一种取巧的写法，利用逆时针或者顺时针旋转方向，判断方块左右侧是否有相关的方块
    private boolean isRelatedInDirection(WorldAccess access, BlockPos pos, Direction direction, boolean counterclockwise) {
        Direction rotatedOnce = counterclockwise ? direction.rotateYCounterclockwise() : direction.rotateYClockwise();
        return this.isRelatedBlock(access, pos, rotatedOnce, direction);
    }

    // 自定义方法isRelatedBlock，判断方块是否为当前方块，并且方向是否为指定方向（毕竟沙发得朝同一个发现吧）
    private boolean isRelatedBlock(WorldAccess access, BlockPos pos, Direction direction1, Direction direction2){
        BlockState state = access.getBlockState(pos.offset(direction1));
        if (state.getBlock() == this){
            Direction blockDirection = state.get(FACING);
            return blockDirection.equals(direction2);
        }
        return false;
    }

    // 内置的枚举类，用于表示沙发方块的类型（左、中、右、单个）
    public enum Type implements StringIdentifiable {
        SINGLE("single"),
        LEFT("left"),
        RIGHT("right"),
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
