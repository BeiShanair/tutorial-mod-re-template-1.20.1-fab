package com.besson.tutorial.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilies {
    /* 这个类用于同类型的方块批量数据生成
       如原版的橡木木板，延伸而出的楼梯、台阶、按钮、压力板等等
       参见原版的BlockFamilies
     */

    // 复制自原版类的一个Map变量
    private static final Map<Block, BlockFamily> BASE_BLOCKS_TO_FAMILIES = Maps.<Block, BlockFamily>newHashMap();

    // 这里面注册的便是由ICE_ETHER_BLOCK延伸出来的所有方块
    public static final BlockFamily ICE_ETHER = register(ModBlocks.ICE_ETHER_BLOCK)
            .stairs(ModBlocks.ICE_ETHER_STAIRS)
            .slab(ModBlocks.ICE_ETHER_SLAB)
            .button(ModBlocks.ICE_ETHER_BUTTON)
            .pressurePlate(ModBlocks.ICE_ETHER_PRESSURE_PLATE)
            .fence(ModBlocks.ICE_ETHER_FENCE)
            .fenceGate(ModBlocks.ICE_ETHER_FENCE_GATE)
            .wall(ModBlocks.ICE_ETHER_WALL)
            .door(ModBlocks.ICE_ETHER_DOOR)
            .trapdoor(ModBlocks.ICE_ETHER_TRAPDOOR)
            .build();

    // 原版的注册方法
    public static BlockFamily.Builder register(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockFamily = (BlockFamily)BASE_BLOCKS_TO_FAMILIES.put(baseBlock, builder.build());
        if (blockFamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + Registries.BLOCK.getId(baseBlock));
        } else {
            return builder;
        }
    }
    // 这个也是原版的方法，用于数据生成的调用
    public static Stream<BlockFamily> getFamilies() {
        return BASE_BLOCKS_TO_FAMILIES.values().stream();
    }
}
