package com.besson.tutorial.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    /* 编写食物之前我们得先写食物组件，即FoodComponent
       原版的食物组件类为FoodComponents
       在那定义了原版所有食物的属性，包括回复的饥饿值，饱和度，效果等等
     */

    /* hunger定义回复的饥饿值，saturationModifier定义饱和度，statusEffect定义效果
       另外还有像alwaysEdible，这是不管你的饥饿值是否为满，都可以继续吃
       snack为小吃，吃的速度比一般食物快
       meat为肉类食物，可以喂给你的狗吃
       etc.

       关于饱和度，在Wiki上也有解释，饱和度是在消耗饥饿值之前被消耗的东西（最高不超过20），也就是当我们的饥饿值HUD跳动的时候，它就正在被消耗
       我们也可以在原版的HungerManager中看到其计算方法，即hunger * saturationModifier * 2.0F，拿金胡萝卜核算一下6*1.2*2.0=14.4，所以金胡萝卜的饱和度是14.4
     */
    public static final FoodComponent CORN = new FoodComponent.Builder().hunger(3).saturationModifier(0.7F).build();
    public static final FoodComponent STRAWBERRY = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).
            statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200), 0.2F).build();
    public static final FoodComponent CHEESE = new FoodComponent.Builder().hunger(4).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200), 0.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200), 0.2F).build();
}
