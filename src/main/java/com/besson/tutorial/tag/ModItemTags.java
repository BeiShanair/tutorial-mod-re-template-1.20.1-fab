package com.besson.tutorial.tag;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    /* Tag，即标签，一种用于物品或者方块等东西分类的标识，可用于物品判断、配方、战利品列表等多个地方
       详见Wiki，https://zh.minecraft.wiki/w/%E6%A0%87%E7%AD%BE
       原版的ItemTag可见ItemTags类
     */

    // 创建我们自己的物品标签
    public static final TagKey<Item> SUGAR_INGREDIENTS = of("sugar_ingredients"); // 糖类原料

    // 注册方法，与方块的类似
    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(TutorialModRe.MOD_ID, id));
    }
}
