package com.besson.tutorial.sound;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    // 原版的声音事件参见SoundEvents类
    // 注册声音事件
    public static final SoundEvent TEST = register("test");

    /* 同样的，我们可以注册更多的声音事件，用于组成声音组，给方块使用
       不过要注意，声音组的声音事件是有顺序的，分别是破坏、踩踏、放置、击打、掉落
     */
    public static final SoundEvent BLOCK_BREAK = register("block_break");
    public static final SoundEvent BLOCK_STEP = register("block_step");
    public static final SoundEvent BLOCK_PLACE = register("block_place");
    public static final SoundEvent BLOCK_HIT = register("block_hit");
    public static final SoundEvent BLOCK_FALL = register("block_fall");

    public static final BlockSoundGroup BLOCK_SOUND_GROUP = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_BREAK, BLOCK_STEP, BLOCK_PLACE, BLOCK_HIT, BLOCK_FALL);
    /* 由原版改编的注册方法
     * 在1.20中，绝大多数的声音事件是SoundEvent类型的，一部分为RegistryEntry.Reference<SoundEvent>类型
     * 由于我暂时还未找到这两者确切的关系，我们猜测（只是猜测），前者用于确切的声音事件，可以直接操作并播放
     * 后者是一种可变注册项的声音事件，根据源代码推测，其基本用于环境音效
     */
    private static SoundEvent register(String name) {
        Identifier id = new Identifier(TutorialModRe.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
    }
}
