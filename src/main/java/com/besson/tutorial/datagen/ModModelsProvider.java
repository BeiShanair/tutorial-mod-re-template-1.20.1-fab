package com.besson.tutorial.datagen;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.ModBlockFamilies;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.custom.CornCrop;
import com.besson.tutorial.block.custom.SofaBlock;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ArmorItem;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

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

        // 同样在本次教程中，我们也采用另外一种方法来生成作物的模型文件，即像甜浆果那样的十字交叉型
        blockStateModelGenerator.blockStateCollector.accept(
                        VariantsBlockStateSupplier.create(ModBlocks.CORN_CROP)
                                .coordinate(BlockStateVariantMap.create(CornCrop.AGE)
                                                .register(stage -> BlockStateVariant.create()
                                                                .put(VariantSettings.MODEL, blockStateModelGenerator.createSubModel(
                                                                        ModBlocks.CORN_CROP, "_stage" + stage, Models.CROSS, TextureMap::cross))
                                                )
                                )
                );
        // 这里我们采用的方块模型文件为BlockBench制作，所以无需生成方块的模型文件，只要简单生成其方块状态文件即可
        blockStateModelGenerator.registerSimpleState(ModBlocks.ORANGE_NIGHTSTAND);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.SIMPLE_ORANGE_CLOCK);

        // 下面一大堆为生成沙发方块状态文件的东西
        Identifier left = new Identifier(TutorialModRe.MOD_ID, "block/sofa_left");
        Identifier right = new Identifier(TutorialModRe.MOD_ID, "block/sofa_right");
        Identifier middle = new Identifier(TutorialModRe.MOD_ID, "block/sofa_middle");
        Identifier single = new Identifier(TutorialModRe.MOD_ID, "block/sofa");
        blockStateModelGenerator.blockStateCollector
                .accept(createSofaBlockState(ModBlocks.SOFA, left, right, middle, single));


        blockStateModelGenerator.registerSimpleState(ModBlocks.LAMP_BLOCK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.BED);
        // 创建方块的物品模型我文件，不生成方块的方块状态及方块模型文件
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.PILLAR, ModelIds.getBlockModelId(ModBlocks.PILLAR));

        Identifier postModel = new Identifier(TutorialModRe.MOD_ID, "block/fence_post");
        Identifier sideModel = new Identifier(TutorialModRe.MOD_ID, "block/fence_side");
        blockStateModelGenerator.blockStateCollector
                .accept(createFenceBlockState(ModBlocks.FENCE, postModel, sideModel));

        blockStateModelGenerator.registerSimpleState(ModBlocks.SIMPLE_CABINET);
        blockStateModelGenerator.registerSimpleState(ModBlocks.TEST_GEO_BLOCK);

        // 原木和木头的注册使用registerLog方法
        blockStateModelGenerator.registerLog(ModBlocks.ICE_ETHER_LOG).log(ModBlocks.ICE_ETHER_LOG).wood(ModBlocks.ICE_ETHER_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_ICE_ETHER_LOG).log(ModBlocks.STRIPPED_ICE_ETHER_LOG).wood(ModBlocks.STRIPPED_ICE_ETHER_WOOD);

//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ETHER_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ETHER_LEAVES);

        blockStateModelGenerator.registerTintableCross(ModBlocks.ICE_ETHER_TREE_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.SIMPLE_FLOWER, ModBlocks.POTTED_SIMPLE_FLOWER, BlockStateModelGenerator.TintType.NOT_TINTED);

    }

    public static BlockStateSupplier createFenceBlockState(Block fenceBlock, Identifier postModelId, Identifier sideModelId) {
        return MultipartBlockStateSupplier.create(fenceBlock)
                .with(BlockStateVariant.create().put(VariantSettings.MODEL, postModelId))
                .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideModelId))
                .with(
                        When.create().set(Properties.EAST, true),
                        BlockStateVariant.create().put(VariantSettings.MODEL, sideModelId).put(VariantSettings.Y, VariantSettings.Rotation.R90)
                )
                .with(
                        When.create().set(Properties.SOUTH, true),
                        BlockStateVariant.create().put(VariantSettings.MODEL, sideModelId).put(VariantSettings.Y, VariantSettings.Rotation.R180)
                )
                .with(
                        When.create().set(Properties.WEST, true),
                        BlockStateVariant.create().put(VariantSettings.MODEL, sideModelId).put(VariantSettings.Y, VariantSettings.Rotation.R270)
                );
    }

    public static BlockStateSupplier createSofaBlockState(Block block, Identifier left, Identifier right, Identifier middle, Identifier single) {
        return VariantsBlockStateSupplier.create(block)
                .coordinate(
                        BlockStateVariantMap.create(SofaBlock.TYPE, Properties.HORIZONTAL_FACING)
                                .register(SofaBlock.Type.LEFT, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.LEFT, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.LEFT, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.LEFT, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left))

                                .register(SofaBlock.Type.RIGHT, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.RIGHT, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.RIGHT, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.RIGHT, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right))

                                .register(SofaBlock.Type.MIDDLE, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.MIDDLE, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.MIDDLE, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.MIDDLE, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle))

                                .register(SofaBlock.Type.SINGLE, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.SINGLE, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.SINGLE, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.SINGLE, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single))
                );
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

        itemModelGenerator.register(ModItems.A_MOMENT_APART_MUSIC_DISC, Models.GENERATED);

        itemModelGenerator.register(ModItems.OIL_BUCKET, Models.GENERATED);

        // 悬挂告示牌不会自己生成模型，所以我们要手动生成
        itemModelGenerator.register(ModItems.ICE_ETHER_HANGING_SIGN, Models.GENERATED);

        itemModelGenerator.register(ModItems.ICE_ETHER_BOAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_ETHER_CHEST_BOAT, Models.GENERATED);
    }
}
