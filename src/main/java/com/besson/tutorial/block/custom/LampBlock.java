package com.besson.tutorial.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LampBlock extends Block {
    // 引入方块亮度的是属性，这个属性是一个布尔值，表示方块是否被点亮
    public static final BooleanProperty LIT = Properties.LIT;

    public LampBlock(Settings settings) {
        // 设置方块的光照值，当方块被点亮时，光照值为15，否则为0
        super(settings.luminance(state -> state.get(LIT) ? 15 : 0));
        // 设置方块默认状态
        this.setDefaultState(this.getDefaultState().with(LIT, true));
    }

    // 重写appendProperties方法，添加LIT属性
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    // 重写onUse方法，当玩家右键方块时，切换方块亮度状态
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            if (state.get(LIT)) {
                world.setBlockState(pos, state.with(LIT, false));
            } else {
                world.setBlockState(pos, state.with(LIT, true));
            }
        }
        return ActionResult.SUCCESS;
    }
}
