package com.besson.tutorial.datagen;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import com.besson.tutorial.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    /* 对于粗矿加工成精矿的配方，如粗铁加工为铁锭，我们需要先写一个List<ItemConvertible>
       在List中存放是我们能够加工成同一个精矿的粗矿，比如原版的铁矿石、深层铁矿石和粗铁都可以熔炼成铁
     */

    public static final List<ItemConvertible> ICE_ETHER = List.of(ModItems.RAW_ICE_ETHER);
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        /* 对于9->1，1->9的配方，我们使用offerReversibleCompactingRecipes方法来生成配方文件
           它会自动生成两个配方文件，一个是9->1的配方文件，一个是1->9的配方文件
           其中，前面的一个RecipeCategory和物品是后者合成前者的配方，后面的一个RecipeCategory和物品是前者合成后者的配方
         */
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.ICE_ETHER,
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.ICE_ETHER_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_ICE_ETHER,
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ICE_ETHER_BLOCK);

        // 我们使用熔炉和高炉对应的生成方法写配方文件
        offerSmelting(exporter, ICE_ETHER, RecipeCategory.MISC, ModItems.ICE_ETHER, 0.7F, 200, "ice_ether");
        offerBlasting(exporter, ICE_ETHER, RecipeCategory.MISC, ModItems.ICE_ETHER, 0.7F, 100, "ice_ether");

        // 另外有序合成和无序合成也有对应的方法可以生成
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,ModBlocks.ICE_ETHER_ORE,1)
                .input(ModItems.RAW_ICE_ETHER)
                .input(Blocks.STONE)
                .criterion(hasItem(Blocks.STONE),conditionsFromItem(Blocks.STONE))
                .criterion(hasItem(ModItems.ICE_ETHER),conditionsFromItem(ModItems.ICE_ETHER))
                .offerTo(exporter,new Identifier(TutorialModRe.MOD_ID, getRecipeName(ModBlocks.ICE_ETHER_ORE)));

        // 有了Tag以后，我们就可以改写我们的配方
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SUGAR,3)
                .pattern("###")
                .input('#', ModItemTags.SUGAR_INGREDIENTS)
                .criterion(hasItem(Items.BEETROOT),conditionsFromTag(ModItemTags.SUGAR_INGREDIENTS))
                .offerTo(exporter,new Identifier(TutorialModRe.MOD_ID,"beetroot_to_sugar"));

        // 对于食物类的加工配方，如烟熏炉和营火配方，可以直接使用RecipeProvider.offerFoodCookingRecipe来写
        RecipeProvider.offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING,
                600, ModItems.RAW_ICE_ETHER, ModItems.ICE_ETHER, 0.35F);
    }
}
