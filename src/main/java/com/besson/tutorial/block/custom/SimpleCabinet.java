package com.besson.tutorial.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SimpleCabinet extends AbstractChestBlock<SimpleCabinetBE> {
    /* 方块实体，本质上还是为方块
     * 但方块本身并不能存储数据，或是加工某些东西，所以需要引入方块实体
     * 这个概念其实和ItemStack类似，因为物品同样不能存储数据，而ItemStack可以
     *
     * 一般而言，创建方块实体的步骤为：（但是这个顺序并不一定，因为在实际开发过程中，可能是这里写一点，那里写一点的）
     * 1. 创建方块类，继承BlockWithEntity
     * 2. 创建方块实体类，继承BlockEntity
     * 3. 注册方块实体、注册方块（不带GUI或者使用原版的GUI一般就到这里）
     * 4. 创建屏幕类，继承HandledScreen
     * 5. 创建屏幕处理器类，继承ScreenHandler
     * 6. 注册屏幕处理器类和屏幕类
     * ...等等（上面只是最基本的步骤，并不排除其他辅助类的编写）
     *
     * 所以如果从头到尾完整写一个带GUI的方块实体，其编写过程相当复杂
     * PS:1.21案例中的方块实体2，我自己录制的视频时间长达1小时30分钟，这还是有事先准备的前提下
     *
     * 由于这里的教程直接采用原版的箱子相关内容，所以会比较简单
     */

    public SimpleCabinet(Settings settings, Supplier<BlockEntityType<? extends SimpleCabinetBE>> blockEntityTypeSupplier) {
        super(settings, blockEntityTypeSupplier);
    }

    // 因为两个箱子组合在一起的时候会变成一个大箱子，所以这里会有DoubleBlockProperties，不过我们这里并不需要用到
    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return null;
    }

    // 获取方块实体的渲染类型
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // 玩家右键使用方块，开启GUI
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            NamedScreenHandlerFactory factory = this.createScreenHandlerFactory(state, world, pos);
            if (factory != null) {
                player.openHandledScreen(factory);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    // 当方块状态改变（如破坏），会调用这个方法（破坏的话就将箱子的东西掉落出来）
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity block = world.getBlockEntity(pos);
            if (block instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) block);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    // 创建方块实体
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SimpleCabinetBE(pos, state);
    }
}
