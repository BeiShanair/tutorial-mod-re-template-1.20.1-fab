package com.besson.tutorial.datagen;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

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
