package com.besson.tutorial.datagen;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.custom.CornCrop;
import com.besson.tutorial.block.custom.StrawberryCrop;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PotatoesBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        // addDrop方法生成的战利品列表为方块本身
        addDrop(ModBlocks.ICE_ETHER_BLOCK);
        addDrop(ModBlocks.RAW_ICE_ETHER_BLOCK);

        // 在addDrop方法中，与其他方法（LootTable.Builder）结合可写出不同的战利品列表，比如矿石的oreDrops方法
//        addDrop(ModBlocks.ICE_ETHER_ORE, oreDrops(ModBlocks.ICE_ETHER_ORE, ModItems.RAW_ICE_ETHER));

        addDrop(ModBlocks.ICE_ETHER_ORE, likeCopperOreDrops(ModBlocks.ICE_ETHER_ORE, ModItems.RAW_ICE_ETHER, 1.0F, 3.0F));

        /* 设置战利品列表
           其中台阶和门还要写单独方法
           因为台阶在变成完整方块时，需要掉落两个
           而门具有半门特性，也需要单独设置
         */
        addDrop(ModBlocks.ICE_ETHER_STAIRS);
        addDrop(ModBlocks.ICE_ETHER_SLAB, slabDrops(ModBlocks.ICE_ETHER_SLAB));
        addDrop(ModBlocks.ICE_ETHER_BUTTON);
        addDrop(ModBlocks.ICE_ETHER_PRESSURE_PLATE);
        addDrop(ModBlocks.ICE_ETHER_FENCE);
        addDrop(ModBlocks.ICE_ETHER_FENCE_GATE);
        addDrop(ModBlocks.ICE_ETHER_DOOR, doorDrops(ModBlocks.ICE_ETHER_DOOR));
        addDrop(ModBlocks.ICE_ETHER_WALL);
        addDrop(ModBlocks.ICE_ETHER_TRAPDOOR);

        /* 这里的作物掉落物是通过LootCondition来判断的
           倘若作物的生长阶段为5，那么掉落的物品为ModItems.STRAWBERRY，否则掉落的物品为ModItems.STRAWBERRY_SEEDS
           不带种子的作物可以参考POTATO、CARROT等作物的掉落物
         */
        BlockStatePropertyLootCondition.Builder builder =
                BlockStatePropertyLootCondition.builder(ModBlocks.STRAWBERRY_CROP)
                        .properties(StatePredicate.Builder.create().exactMatch(StrawberryCrop.AGE, 5));
        addDrop(ModBlocks.STRAWBERRY_CROP, cropDrops(ModBlocks.STRAWBERRY_CROP, ModItems.STRAWBERRY, ModItems.STRAWBERRY_SEEDS, builder));

        // 在本次的教程中，我们讲CORN直接作为作物的种子，所有这里的战利品列表写法也有所不同（参见马铃薯、胡萝卜的写法）
        LootCondition.Builder builder2 = BlockStatePropertyLootCondition.builder(ModBlocks.CORN_CROP)
                .properties(StatePredicate.Builder.create().exactMatch(CornCrop.AGE, 8));
        addDrop(ModBlocks.CORN_CROP,
                this.applyExplosionDecay(
                        ModBlocks.CORN_CROP,
                        LootTable.builder()
                                .pool(LootPool.builder().with(ItemEntry.builder(ModItems.CORN)))
                                .pool(
                                        LootPool.builder()
                                                .conditionally(builder2)
                                                .with(ItemEntry.builder(ModItems.CORN).apply(ApplyBonusLootFunction.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3)))
                                )
                ));
        addDrop(ModBlocks.ICE_ETHER_LOG);
        addDrop(ModBlocks.ICE_ETHER_WOOD);
        addDrop(ModBlocks.STRIPPED_ICE_ETHER_LOG);
        addDrop(ModBlocks.STRIPPED_ICE_ETHER_WOOD);
        addDrop(ModBlocks.ICE_ETHER_PLANKS);
        addDrop(ModBlocks.ICE_ETHER_LEAVES, leavesDrops(ModBlocks.ICE_ETHER_LEAVES, Blocks.OAK_SAPLING, LEAVES_STICK_DROP_CHANCE));
    }

    /* 我们可以到原版的BlockLootTableGenerator中查看方块的战利品列表生成方法
       并且我们可以利用这些方法来生成我们自己的战利品列表
       这里我们以铜矿石的战利品列表为例，生成我们自己矿石掉落多个的方法
     */
    public LootTable.Builder likeCopperOreDrops(Block drop, Item item, float minCount, float maxCount) {
        /* 这里的dropWithSilkTouch方法会生成带有精准采集附魔情况的战利品列表
           也就是说如果你的采集工具没有精准采集的附魔，那么就掉落矿石
           如果你的采集工具带有精准采集的附魔，那么就掉落矿石方块本身
         */
        return dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minCount, maxCount)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }
}
