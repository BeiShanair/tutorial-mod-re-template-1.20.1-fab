package com.besson.tutorial.block.custom;

import com.besson.tutorial.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CornCrop extends CropBlock {
    // 设置第一阶段和第二阶段的成熟阶段
    public static final int FIRST_STAGE_AGE = 7;
    public static final int SECOND_STAGE_AGE = 1;
    // 设置作物的成熟阶段属性，由于原版并不存在AGE属性为8的，因此我们自定义一个
    public static final IntProperty AGE = IntProperty.of("age",0,8);
    // 作物外轮廓线同理，我们需要自行设置
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)};

    public CornCrop(Settings settings) {
        super(settings);
    }

    // 重写getOutlineShape，设置作物的外轮廓线
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(AGE)];
    }

    // 重写appendProperties，设置作物的成熟阶段属性
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_AGE + SECOND_STAGE_AGE;
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.CORN;
    }

    // 重写canPlaceAt，因为在多方块作物中，上方生长出来的作物需要检测其下方是否有相应的作物方块（参见仙人掌、甘蔗之类的作物）
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        // 获取当前方块下方的方块状态
        BlockState state1 = world.getBlockState(pos.down());
        return super.canPlaceAt(state, world, pos) ||
                state1.isOf(this) && state1.get(AGE) == FIRST_STAGE_AGE;
    }

    // 重写applyGrowth，设置作物的生长逻辑，此方法一般在用骨粉催熟时调用
    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        // 获取作物当前的生长阶段，并加上随机的生长值（2-5）
        int nextAge = this.getAge(state) + this.getGrowthAmount(world);
        // 作物的最大生长阶段
        int maxAge = this.getMaxAge();
        // 如果下一个生长阶段大于最大生长阶段，则将下一个生长阶段设置为最大生长阶段
        if (nextAge > maxAge){
            nextAge = maxAge;
        }
        /* 获取当前方块上方的方块状态
         * 如果当前方块处于第一生长阶段的最大值，并且上方方块为空气，则将上方方块设置为下一生长阶段的方块状态
         * 否则，将当前方块设置为下一生长阶段的方块状态
         */
        BlockState upState = world.getBlockState(pos.up());
        if (this.getAge(state) == FIRST_STAGE_AGE && upState.isOf(Blocks.AIR)) {
            world.setBlockState(pos.up(), this.withAge(nextAge), Block.NOTIFY_LISTENERS);
        } else {
            world.setBlockState(pos, this.withAge(nextAge - 1), Block.NOTIFY_LISTENERS);
        }
    }

    // 重写randomTick，作物的随机刻生长逻辑
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // 获取当前方块的生长阶段
        int age = this.getAge(state);
        // 获取湿度（父级的方法）
        float f = getAvailableMoisture(this, world, pos);
        // 如果当前方块处于光照充足、湿度充足、生长阶段小于最大生长阶段的情况下，随机生成下一生长阶段的方块状态
        if (world.getBaseLightLevel(pos, 0) >= 9 && random.nextInt((int) (25.0F / f) + 1) == 0
                && age < this.getMaxAge()) {

            if (age == FIRST_STAGE_AGE) {
                BlockState blockState = world.getBlockState(pos.up());
                if (blockState.isOf(Blocks.AIR)) {
                    world.setBlockState(pos.up(), this.withAge(age + 1), Block.NOTIFY_LISTENERS);
                }
            } else {
                world.setBlockState(pos, this.withAge(age + 1), Block.NOTIFY_LISTENERS);
            }
        }
    }
}
