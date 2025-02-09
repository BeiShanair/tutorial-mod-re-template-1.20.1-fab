package com.besson.tutorial.datagen;

import com.besson.tutorial.block.ModBlockFamilies;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ArmorItem;
import net.minecraft.state.property.Properties;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // 这个方法下写我们的方块，它会将我们的方块模型文件、方块状态文件，以及方块对应的方块物品模型文件一起生成

        // 我们之前写的方块是cube all的，那么这里我们就可以采用registerSimpleCubeAll方法来生成我们的方块模型文件
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ETHER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_ICE_ETHER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ETHER_ORE);

        /* 参考原版代码编写同类型方块的数据生成
           其中的registerCubeAllModelTexturePool方法获取的是基础方块的材质
           并将其作为同家族中所有方块的材质
         */
        ModBlockFamilies.getFamilies()
                .filter(BlockFamily::shouldGenerateModels).forEach(
                        blockFamily ->
                                blockStateModelGenerator.registerCubeAllModelTexturePool(blockFamily.getBaseBlock())
                                        .family(blockFamily));

        // 作物的模型文件需要罗列出其所有的生长阶段
        blockStateModelGenerator.registerCrop(ModBlocks.STRAWBERRY_CROP, Properties.AGE_5, 0, 1, 2, 3, 4, 5);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // 这个方法下写我们的物品，它生成我们的物品模型文件

        // 利用register方法注册我们的物品，并指定它的模型类型
        itemModelGenerator.register(ModItems.ICE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ICE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.CARDBOARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.STRAWBERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHEESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANTHRACITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_ETHER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PICKAXE_AXE, Models.HANDHELD);

        // 盔甲物品的模型与其他物品不同，有单独的方法
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_BOOTS);

        itemModelGenerator.register(ModItems.ICE_ETHER_HORSE_ARMOR, Models.GENERATED);
    }
}
