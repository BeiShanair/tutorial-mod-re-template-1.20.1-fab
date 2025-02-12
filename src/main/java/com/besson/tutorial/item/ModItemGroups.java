package com.besson.tutorial.item;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    /* 接下来我们来新建物品栏
       原版的物品栏参考ItemGroups类
     */

    // 注册物品栏的注册键
    public static final RegistryKey<ItemGroup> TUTORIAL_GROUP = register("tutorial_group");

    // 原版的注册方法，当然我们要改命名空间（再回到上面注册物品栏的注册键）
    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(TutorialModRe.MOD_ID, id));
    }

    //也许你觉得原版的方法还是比较复杂，我们也可以利用registerAndGetDefault方法返回的是ItemGroup类型的变量这个特性，并结合我们之前写过的物品
    public static final ItemGroup TUTORIAL_GROUP2 = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(TutorialModRe.MOD_ID, "tutorial_group2"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.tutorial_group2"))
                    .icon(() -> new ItemStack(ModItems.CARDBOARD))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CARDBOARD);
                        entries.add(Items.DIAMOND);
                        entries.add(ModBlocks.ICE_ETHER_BLOCK);
                        entries.add(ModBlocks.RAW_ICE_ETHER_BLOCK);
                        entries.add(ModBlocks.ICE_ETHER_ORE);
                        entries.add(ModItems.RAW_ICE_ETHER);
                        entries.add(ModItems.CORN);
                        entries.add(ModItems.STRAWBERRY);
                        entries.add(ModItems.CHEESE);
                        entries.add(ModItems.ANTHRACITE);
                        entries.add(ModBlocks.ICE_ETHER_STAIRS);
                        entries.add(ModBlocks.ICE_ETHER_SLAB);
                        entries.add(ModBlocks.ICE_ETHER_BUTTON);
                        entries.add(ModBlocks.ICE_ETHER_PRESSURE_PLATE);
                        entries.add(ModBlocks.ICE_ETHER_FENCE);
                        entries.add(ModBlocks.ICE_ETHER_FENCE_GATE);
                        entries.add(ModBlocks.ICE_ETHER_WALL);
                        entries.add(ModBlocks.ICE_ETHER_DOOR);
                        entries.add(ModBlocks.ICE_ETHER_TRAPDOOR);
                        entries.add(ModItems.FIRE_ETHER);
                        entries.add(ModItems.FIRE_ETHER_SWORD);
                        entries.add(ModItems.FIRE_ETHER_SHOVEL);
                        entries.add(ModItems.FIRE_ETHER_AXE);
                        entries.add(ModItems.FIRE_ETHER_HOE);
                        entries.add(ModItems.FIRE_ETHER_PICKAXE);
                        entries.add(ModItems.PICKAXE_AXE);
                        entries.add(ModItems.ICE_ETHER_HELMET);
                        entries.add(ModItems.ICE_ETHER_CHESTPLATE);
                        entries.add(ModItems.ICE_ETHER_LEGGINGS);
                        entries.add(ModItems.ICE_ETHER_BOOTS);
                        entries.add(ModItems.ICE_ETHER_HORSE_ARMOR);
                        entries.add(ModItems.STRAWBERRY_SEEDS);
                        entries.add(ModItems.A_MOMENT_APART_MUSIC_DISC);
                        entries.add(ModBlocks.ORANGE_NIGHTSTAND);
                        entries.add(ModItems.BASEBALL_BAT);
                        entries.add(ModBlocks.SIMPLE_ORANGE_CLOCK);
                        entries.add(ModBlocks.SOFA);
                    }).build());

    // 初始化注册方法
    public static void registerModItemGroups() {
        // 这里本来是不用写什么的，我们可以将注册语句写在这里
        // 在原始的注册语句中，我们可以看到一个Registry<ItemGroup>类型的registry，这个其实就是注册表类型，可直接调用原版注册表的东西
        // 注册参数分别是注册表项、注册键、物品栏（其中物品栏创建参数可再分为物品栏位置、展示名字、图标文件、物品栏内容等，不要忘了最后的build）
        Registry.register(
                Registries.ITEM_GROUP,
                TUTORIAL_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemGroup.tutorial_group"))
                        .icon(() -> new ItemStack(ModItems.ICE_ETHER))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.ICE_ETHER);
                            entries.add(ModItems.CARDBOARD);
                        }).build());
    }
}
