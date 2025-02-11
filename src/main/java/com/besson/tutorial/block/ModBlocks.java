package com.besson.tutorial.block;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.custom.CornCrop;
import com.besson.tutorial.block.custom.StrawberryCrop;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
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
    public static final Block ICE_ETHER_BLOCK = register("ice_ether_block", new Block(AbstractBlock.Settings.create().strength(0.2f,0.2f).nonOpaque()));

    public static final Block RAW_ICE_ETHER_BLOCK = register("raw_ice_ether_block", new Block(AbstractBlock.Settings.create().strength(0.2f,0.2f).requiresTool()));

    public static final Block ICE_ETHER_ORE = register("ice_ether_ore", new Block(AbstractBlock.Settings.create().strength(0.2f,0.2f).requiresTool()));

    // 楼梯，接受参数为基础方块状态和方块设置
    public static final Block ICE_ETHER_STAIRS = register("ice_ether_stairs",
            new StairsBlock(ICE_ETHER_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    // 台阶，接受参数为方块设置
    public static final Block ICE_ETHER_SLAB = register("ice_ether_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    // 按钮，接受参数为方块设置，方块类型，按下至弹起的时间（tick），是否为木制（即是否能被箭、三叉戟激活）
    public static final Block ICE_ETHER_BUTTON = register("ice_ether_button",
            new ButtonBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), BlockSetType.STONE, 100, false));
    // 压力板，接受参数为激活条件，方块设置，方块类型
    public static final Block ICE_ETHER_PRESSURE_PLATE = register("ice_ether_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), BlockSetType.STONE));
    // 栅栏，接受参数为方块设置
    public static final Block ICE_ETHER_FENCE = register("ice_ether_fence",
            new FenceBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    // 栅栏门，接受参数为方块设置，木头类型（因为原版的栅栏门都是木制的，而栅栏中只有下界砖栅栏不是木制的）
    public static final Block ICE_ETHER_FENCE_GATE = register("ice_ether_gate",
            new FenceGateBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), WoodType.OAK));
    // 墙，接受参数为方块设置
    public static final Block ICE_ETHER_WALL = register("ice_ether_wall",
            new WallBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    // 门，接受参数为方块设置，方块类型（其中如果设置为铁（IRON），则必须用红石信号才能打开）
    public static final Block ICE_ETHER_DOOR = register("ice_ether_door",
            new DoorBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), BlockSetType.IRON));
    // 活板门，接受参数为方块设置，方块类型（同上）
    public static final Block ICE_ETHER_TRAPDOOR = register("ice_ether_trapdoor",
            new TrapdoorBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK).nonOpaque(), BlockSetType.STONE));

    /* 作物，相比之下，因为作物方块没有对应的方块物品，所以我们直接用Registry.register注册
       其中，实例化的类也是我们创建的StrawberryCrop，接受参数为方块设置
       noCollision代表这个方块没有碰撞箱，ticksRandomly代表这个方块有随机刻（对于作物这种会生长的方块而言，这是必须的）
       breakInstantly代表这个方块可以瞬间破坏，pistonBehavior代表活塞对这个方块的行为（DESTROY代表破坏）
     */
    public static final Block STRAWBERRY_CROP = Registry.register(Registries.BLOCK,
            new Identifier(TutorialModRe.MOD_ID, "strawberry_crop"),
            new StrawberryCrop(AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block CORN_CROP = Registry.register(Registries.BLOCK,
            new Identifier(TutorialModRe.MOD_ID, "corn_crop"),
            new CornCrop(AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().pistonBehavior(PistonBehavior.DESTROY)));

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
