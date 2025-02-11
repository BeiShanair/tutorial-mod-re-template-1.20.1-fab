package com.besson.tutorial.util;

import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

public class ModCustomTrades {
    /* 要添加村民交易我们可以借助Fabric的API
     * TradeOfferHelper.registerVillagerOffers，其中参数为村民职业，交易等级，以及交易条目
     */
    public static void registerCustomTrades(){
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER,1,
                factories -> {
                    // BuyForOneEmeraldFactory为玩家向村民出售物品，其中参数依次为物品，价格（物品数量），最大交易数量，村民获得的经验（不过，此条交易项目只能获得一个绿宝石）
                    factories.add(new TradeOffers.BuyForOneEmeraldFactory(ModItems.CORN, 2, 12, 5));
                    // SellItemFactory为村民向玩家出售物品，其中参数依次为物品，价格，物品数量，村民获得的经验，交易折扣
                    factories.add(new TradeOffers.SellItemFactory(ModItems.STRAWBERRY_SEEDS.getDefaultStack(), 1, 12, 5, 2, 0.5F));
                    // 由于在1.20.1中，村民交易的各种方法并未像1.21中那样完善，如果要自定义玩家向村民出售物品所获得的绿宝石数量，需要自行实例化TradeOffer
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.STRAWBERRY,5),
                            new ItemStack(Items.EMERALD,6),
                            2,7,0.075f
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> {
                    // ProcessItemFactory为玩家向村民购买物品，不过需要玩家提供一定的原材料，而后交易得到加工过的物品，其中参数依次为原材料，原材料数量，价格（绿宝石），加工后的物品，加工后的物品数量，最大交易数量，村民获得的经验
                    factories.add(new TradeOffers.ProcessItemFactory(Items.MILK_BUCKET, 1, 2, ModItems.CHEESE, 3, 16, 1));
        });

        // 在1.20.1中，附魔还没有变成数据驱动的，我们可以通过直接实例化TradeOffer来添加特定附魔的交易
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ICE_ETHER,16),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(Enchantments.SHARPNESS,2)),
                            3,12,0.05f));
                });

    }
}
