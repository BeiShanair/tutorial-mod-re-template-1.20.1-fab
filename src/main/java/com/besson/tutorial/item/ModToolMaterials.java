package com.besson.tutorial.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
    /*
       这是一个枚举类，可参见原版的ToolMaterials，它同样实现了ToolMaterial这个接口
     */
    FIRE_ETHER(4, 2031, 12.0F, 4.0F, 50,
            () -> Ingredient.ofItems(ModItems.FIRE_ETHER));

    // 下面是定义工具材料的基本属性
    // 采集等级，原版最高为下界合金，等级为4
    private final int miningLevel;
    // 耐久
    private final int itemDurability;
    // 基础采集速度（结合下面的方法，其实质为一个系数）
    private final float miningSpeed;
    // 基础攻击伤害；采集速度和攻击伤害在注册时还将进一步进行设置
    private final float attackDamage;
    // 附魔能力，数值越高则在附魔台得到的附魔越好
    private final int enchantability;
    // 修复材料
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
