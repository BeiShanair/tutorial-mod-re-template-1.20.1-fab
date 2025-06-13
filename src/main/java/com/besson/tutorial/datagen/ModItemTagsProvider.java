package com.besson.tutorial.datagen;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import com.besson.tutorial.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {

    // 注意在创建super函数的时候，选择带有两个参数的构造函数，三个参数的那个其中一个是BlockTags的
    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModItemTags.SUGAR_INGREDIENTS)
                .add(ModItems.STRAWBERRY)
                .add(ModItems.CHEESE)
                .add(Items.BEETROOT);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
                .add(ModItems.A_MOMENT_APART_MUSIC_DISC);

        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.ICE_ETHER_PLANKS.asItem());
        getOrCreateTagBuilder(ItemTags.LOGS)
                .add(ModBlocks.ICE_ETHER_LOG.asItem())
                .add(ModBlocks.ICE_ETHER_WOOD.asItem())
                .add(ModBlocks.STRIPPED_ICE_ETHER_LOG.asItem())
                .add(ModBlocks.STRIPPED_ICE_ETHER_WOOD.asItem());
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ICE_ETHER_LOG.asItem())
                .add(ModBlocks.ICE_ETHER_WOOD.asItem())
                .add(ModBlocks.STRIPPED_ICE_ETHER_LOG.asItem())
                .add(ModBlocks.STRIPPED_ICE_ETHER_WOOD.asItem());

    }
}
