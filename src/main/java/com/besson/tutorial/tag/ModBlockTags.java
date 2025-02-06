package com.besson.tutorial.tag;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    /* Tag，即标签，一种用于物品或者方块等东西分类的标识，可用于物品判断、配方、战利品列表等多个地方
       详见Wiki，https://zh.minecraft.wiki/w/%E6%A0%87%E7%AD%BE
       原版的ItemTag可见ItemTags类
     */

    // 创建我们的方块标签
    public static final TagKey<Block> ETHER_BLOCK = of("ether_block");

    // 创建一个标签，用于我们PickAxe物品的方块判断
    public static final TagKey<Block> PICKAXE_AXE_MINEABLE = of("pickaxe_axe_mineable");

    // 注册方法，与物品的类似
    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(TutorialModRe.MOD_ID, id));
    }
}
