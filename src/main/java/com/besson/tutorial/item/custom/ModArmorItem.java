package com.besson.tutorial.item.custom;

import com.besson.tutorial.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {
    /* 要实现全套盔甲的效果，我们需要创建一个物品类，继承自ArmorItem（因为也要同时实现盔甲物品的效果）
       而后我们要重写inventoryTick方法，这个方法会实时调用（和tick挂钩的，与游戏刻的速度一样，每秒被调用20次）
     */

    /* 创建一个静态的Map，用来存储盔甲材料和对应的效果，这里的效果采用List<StatusEffectInstance>来存储，这样就能实现多个效果叠加
       其中StatusEffectInstance的参数为：效果，持续时间（单位为tick，此处为了实现脱下盔甲效果立即消失的效果，所以设置为1），
       效果倍率（比等级小1），是否来自特殊物体（信标、潮涌核心），是否显示粒子，是否显示效果图标
     */
    private static final Map<ArmorMaterial, List<StatusEffectInstance>> MATERIAL_STATUS_EFFECT_INSTANCE_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, List<StatusEffectInstance>>())
                    .put(ModArmorMaterials.ICE_ETHER,
                            Arrays.asList(
                                    new StatusEffectInstance(StatusEffects.SPEED,1,1, false,false,true),
                                    new StatusEffectInstance(StatusEffects.JUMP_BOOST,1,1, false,false,true)
                                    )).build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    // 重写inventoryTick方法，当玩家穿上全套盔甲时，会触发效果
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            // 这里的实体可以判定是否为玩家实体，毕竟我们不希望其他生物（如僵尸）穿上同样的盔甲后也获得效果
            // hasFullSuitableArmor方法用来判定玩家是否穿上了全套盔甲，evaluateArmorEffects方法用来给玩家添加效果
            if (entity instanceof PlayerEntity player && hasFullSuitableArmor(player)) {
                evaluateArmorEffects(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // 给玩家添加效果
    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, List<StatusEffectInstance>> entry : MATERIAL_STATUS_EFFECT_INSTANCE_MAP.entrySet()) {
            ArmorMaterial material = entry.getKey();
            List<StatusEffectInstance> statusEffectInstances = entry.getValue();

            if (hasCorrectMaterialArmorOn(material, player)) {
                for (StatusEffectInstance effect : statusEffectInstances) {
                    StatusEffect effect1 = effect.getEffectType();
                    if (!player.hasStatusEffect(effect1)) {
                        player.addStatusEffect(effect);
                    }
                }
            }

        }
    }

    // 判定玩家是否穿上了正确的盔甲
    private boolean hasCorrectMaterialArmorOn(ArmorMaterial material, PlayerEntity player) {
        // 这里的for循环用来遍历玩家身上的盔甲，如果玩家身上的盔甲栏中有不属于盔甲物品的东西（鞘翅），则直接返回false
        for (ItemStack stack : player.getInventory().armor) {
            if (!(stack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = (ArmorItem) player.getInventory().getArmorStack(3).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmorStack(2).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmorStack(1).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmorStack(0).getItem();

        return helmet.getMaterial() == material
                && chestplate.getMaterial() == material
                && leggings.getMaterial() == material
                && boots.getMaterial() == material;
    }

    // 判定玩家是否穿上了全套盔甲
    private boolean hasFullSuitableArmor(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }


}
