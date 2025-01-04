package com.besson.tutorial.block;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    /* 这个类用来注册方块
       与之前注册物品类似
     */

    /* 这里我们注册方块
       对于方块的设置，详见视频教程以及图文教程
     */
    public static final Block ICE_ETHER_BLOCK = register("ice_ether_block", new Block(AbstractBlock.Settings.create().strength(0.2f,0.2f)));

    public static final Block RAW_ICE_ETHER_BLOCK = register("raw_ice_ether_block", new Block(AbstractBlock.Settings.create().strength(0.2f,0.2f).requiresTool()));

    public static final Block ICE_ETHER_ORE = register("ice_ether_ore", new Block(AbstractBlock.Settings.create().strength(0.2f,0.2f).requiresTool()));



    /* 同样的，我们也先去看看源代码的方块注册
       以STONE为例，方块的注册方法就只有一层
       自然，id是要改成我们自己的命名空间的
     */
    public static Block register(String id, Block block) {
        // 在注册方块的同时也将方块物品一起注册了
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, new Identifier(TutorialModRe.MOD_ID, id), block);
    }

    /* 但这并没有结束
       在我们写物品的时候，或许你已经注意到了
       有一类物品是实例化BlockItem的，它们是方块物品
       因为方块在物品栏中，它是物品，而方块放在世界中的时候才是方块
       而这里我们回到Items中，还是以STONE为例，其实实例化的是BlockItem，然后最终又回到了最后一个方法
       注册方块物品你可以在ModItems中实现，不过在这里，方便起见，我们就直接在这个类中进行注册了
     */
    public static void registerBlockItems(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier(TutorialModRe.MOD_ID, id),
                new BlockItem(block, new Item.Settings()));
    }

    // 不要忘了初始化注册方法
    public static void registerModBlocks() {

    }
}
