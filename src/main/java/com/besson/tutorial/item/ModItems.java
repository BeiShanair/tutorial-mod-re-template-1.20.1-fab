package com.besson.tutorial.item;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    /* 在这个类中来注册物品
       Fabric与Forge的注册系统不同，我们可以直接使用Minecraft原生的注册系统进行注册，物品、方块、方块实体等等都可以用这种方式注册
     */

    // 注册我们的第一个物品
    public static final Item ICE_ETHER = registerItem("ice_ether", new Item(new Item.Settings()));

    public static final Item CARDBOARD = registerItems("materials/cardboard", new Item(new Item.Settings()));

    /*
       而采用原生的注册系统，我们不妨先去看看源代码
       以DIAMOND为例，我们可以看到其注册方法是这样的：（一共是三层，DIAMOND注册调用的是第一个方法）
     */
    public static Item register(String id, Item item) {
        /* 这里的Identifier类也就是我们老生常谈的命名空间，也就是ResourceLocation
           这个东西往往会成为模组开发新手的第二块绊脚石（第一块是Gradle的建构），一不小心游戏就崩溃了
           敲重点！！！记上你的小本本！！！Minecraft的命名空间只接受小写字母、数字、下划线、点、斜杠和短横[0-9a-z_.-/]，其余字符均为非法字符，会导致游戏崩溃
           更为详细的内容参见Identifier类
         */
        return register(new Identifier(id), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
        /* 其实最核心的方法在这里，就是这里的return语句
           Registry是原版的注册表系统，包括静态注册表和动态注册表，这里的Item就是静态注册表，未来我们还会用到动态注册表
           Registry.register方法的作用是将一个物品注册到注册表中，返回值是注册的物品
           Registries类定义了Minecraft中所有的注册表，包括物品、方块、方块实体等等，使用getKey方法可以获取注册表的键
         */
        return Registry.register(Registries.ITEM, key, item);
    }

    /* 不过，你是不是觉得叠了那么多层看起来很麻烦
       我们可以将其整合为一个方法，这样看起来就会简洁很多
     */
    public static Item registerItems(String id, Item item) {
        // 整合起来就一句话，当然这里的命名空间得改成你自己的
        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier(TutorialModRe.MOD_ID, id)), item);
    }

    // 好像还是有点长？我们再整合一下
    public static Item registerItem(String id, Item item) {
        /* 这里就要用到Registry.register的另一个方法了
           这个方法接受三个参数，第一个是注册表，第二个是物品的命名空间，第三个是物品本身（其实调用的是上面的那个方法，两者本质上是一样的）
         */
        return Registry.register(Registries.ITEM, new Identifier(TutorialModRe.MOD_ID, id), item);
    }

    // 对了，不要忘记用于初始化的方法
    public static void registerModItems() {
        /* 这里其实啥也不用写，就直接在模组主类中调用这个方法即可
           因为在Java中，调用该方法的时候，所在类的所有静态代码块和静态变量都会被初始化
           我们上面写的物品是static final修饰的，所以在这个方法被调用的时候，物品也就被注册了
         */
    }
}
