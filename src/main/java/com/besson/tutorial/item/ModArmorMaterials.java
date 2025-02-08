package com.besson.tutorial.item;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    // 和工具材料类似，盔甲材料也是一个枚举类，实现了ArmorMaterial接口，不过在1.21中，此类会有所不同

    // 创建盔甲材料
    ICE_ETHER("ice_ether", 37, Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 30, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2.0F, 0.1F, () -> Ingredient.ofItems(ModItems.ICE_ETHER));

    // 盔甲材料的名字
    private final String name;
    // 盔甲材料的耐久度系数，盔甲最终的耐久度由这个系数乘以盔甲各部分的默认耐久度得到（见下方重写的方法）
    private final int durabilityMultiplier;
    // 盔甲材料提供的护甲值，这个是一个EnumMap，表示不同部位的盔甲提供的护甲值
    private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
    // 盔甲材料的附魔能力，与工具材料类似，数值越大则能获得的附魔品质越好
    private final int enchantability;
    // 穿戴盔甲时的声音
    private final SoundEvent equipSound;
    // 盔甲材料的韧性
    private final float toughness;
    // 盔甲材料的击退抗性（原版只有下界合金才有）
    private final float knockbackResistance;
    // 盔甲的修复材料
    private final Supplier<Ingredient> repairIngredientSupplier;

    // 各个部分盔甲的默认耐久度，这个复制自原版类
    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    // 构造函数
    ModArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = repairIngredientSupplier;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return (Integer)BASE_DURABILITY.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return (Integer)this.protectionAmounts.get(type);
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    // 注意它的名字要加上我们的MOD ID，否则它的资源文件又会定位到minecraft下去
    @Override
    public String getName() {
        return TutorialModRe.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
