package com.besson.tutorial.datagen;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItemGroups;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModEnLangProvider extends FabricLanguageProvider {

    // 在创建完构造函数之后，我们可以在super方法中再加入一个参数，这个参数就是我们要翻译的语言
    // 在这里我们使用的是英文，所以传入的是"en_us"（当然不写默认就是en_us）
    public ModEnLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.ICE_ETHER, "Ice Ether");
        translationBuilder.add(ModItems.RAW_ICE_ETHER, "Raw Ice Ether");
        translationBuilder.add(ModItems.CARDBOARD, "Cardboard");
        translationBuilder.add(ModItems.CORN, "Corn");
        translationBuilder.add(ModItems.STRAWBERRY, "Strawberry");
        translationBuilder.add(ModItems.CHEESE, "Cheese");
        translationBuilder.add(ModItems.ANTHRACITE, "Anthracite");
        translationBuilder.add(ModItems.FIRE_ETHER, "Fire Ether");
        translationBuilder.add(ModItems.FIRE_ETHER_AXE, "Fire Ether Axe");
        translationBuilder.add(ModItems.FIRE_ETHER_HOE, "Fire Ether Hoe");
        translationBuilder.add(ModItems.FIRE_ETHER_PICKAXE, "Fire Ether Pickaxe");
        translationBuilder.add(ModItems.FIRE_ETHER_SWORD, "Fire Ether Sword");
        translationBuilder.add(ModItems.FIRE_ETHER_SHOVEL, "Fire Ether Shovel");
        translationBuilder.add(ModItems.PICKAXE_AXE, "Pickaxe Axe");

        translationBuilder.add(ModItems.ICE_ETHER_HELMET, "Ice Ether Helmet");
        translationBuilder.add(ModItems.ICE_ETHER_CHESTPLATE, "Ice Ether Chestplate");
        translationBuilder.add(ModItems.ICE_ETHER_LEGGINGS, "Ice Ether Leggings");
        translationBuilder.add(ModItems.ICE_ETHER_BOOTS, "Ice Ether Boots");

        translationBuilder.add(ModItems.ICE_ETHER_HORSE_ARMOR, "Ice Ether Horse Armor");

        translationBuilder.add(ModItems.STRAWBERRY_SEEDS, "Strawberry Seeds");

        translationBuilder.add(ModItems.BASEBALL_BAT, "Baseball Bat");

        translationBuilder.add(ModItems.OIL_BUCKET, "Oil Bucket");

        translationBuilder.add(ModBlocks.ICE_ETHER_BLOCK, "Ice Ether Block");
        translationBuilder.add(ModBlocks.RAW_ICE_ETHER_BLOCK, "Raw Ice Ether Block");
        translationBuilder.add(ModBlocks.ICE_ETHER_ORE, "Ice Ether Ore");

        translationBuilder.add(ModBlocks.ICE_ETHER_STAIRS, "Ice Ether Stairs");
        translationBuilder.add(ModBlocks.ICE_ETHER_SLAB, "Ice Ether Slab");
        translationBuilder.add(ModBlocks.ICE_ETHER_BUTTON, "Ice Ether Button");
        translationBuilder.add(ModBlocks.ICE_ETHER_PRESSURE_PLATE, "Ice Ether Pressure Plate");
        translationBuilder.add(ModBlocks.ICE_ETHER_FENCE, "Ice Ether Fence");
        translationBuilder.add(ModBlocks.ICE_ETHER_FENCE_GATE, "Ice Ether Fence Gate");
        translationBuilder.add(ModBlocks.ICE_ETHER_WALL, "Ice Ether Wall");
        translationBuilder.add(ModBlocks.ICE_ETHER_DOOR, "Ice Ether Door");
        translationBuilder.add(ModBlocks.ICE_ETHER_TRAPDOOR, "Ice Ether Trapdoor");

        translationBuilder.add(ModBlocks.ORANGE_NIGHTSTAND, "Orange Nightstand");

        translationBuilder.add(ModBlocks.SIMPLE_ORANGE_CLOCK, "Simple Orange Clock");
        translationBuilder.add(ModBlocks.SOFA, "Sofa");
        translationBuilder.add(ModBlocks.LAMP_BLOCK, "Lamp Block");
        translationBuilder.add(ModBlocks.BED, "Bed");
        translationBuilder.add(ModBlocks.PILLAR, "Pillar");
        translationBuilder.add(ModBlocks.FENCE, "Fence");
        translationBuilder.add(ModBlocks.SIMPLE_CABINET, "Simple Cabinet");

        translationBuilder.add(ModItemGroups.TUTORIAL_GROUP, "Tutorial Group");
        translationBuilder.add("itemGroup.tutorial_group2", "Tutorial Group2");

        translationBuilder.add("tooltip.tutorial.pickaxe_axe", "Hold \u00A76Shift\u00A7r for more info!");
        translationBuilder.add("tooltip.tutorial.pickaxe_axe.shift", "This is a tool that can be used as a pickaxe and an axe");

        translationBuilder.add("entity.minecraft.villager.ice_ether_master", "Ice Ether Master");

        translationBuilder.add("sounds.tutorial-mod-re.test", "Test Sound");
        translationBuilder.add("sounds.tutorial-mod-re.block_break", "Block Break Sound");
        translationBuilder.add("sounds.tutorial-mod-re.block_fall", "Block Fall Sound");
        translationBuilder.add("sounds.tutorial-mod-re.block_hit", "Block Hit Sound");
        translationBuilder.add("sounds.tutorial-mod-re.block_place", "Block Place Sound");
        translationBuilder.add("sounds.tutorial-mod-re.block_step", "Block Step Sound");

        translationBuilder.add(ModItems.A_MOMENT_APART_MUSIC_DISC, "A Moment Apart Music Disc");
        translationBuilder.add(ModItems.A_MOMENT_APART_MUSIC_DISC.getTranslationKey() + ".desc", "Odesza - A Moment Apart");

        translationBuilder.add("container.simple_cabinet", "Simple Cabinet");
    }
}
