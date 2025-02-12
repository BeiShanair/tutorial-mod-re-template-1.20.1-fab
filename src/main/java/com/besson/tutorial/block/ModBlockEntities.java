package com.besson.tutorial.block;

import com.besson.tutorial.TutorialModRe;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    // 注册实体（虽然我们这里归类给他归在了方块实体中）
    public static final EntityType<SeatEntity> SEAT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(TutorialModRe.MOD_ID, "seat"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SeatEntity::new).build());

    public static void registerModBlockEntities() {

    }
}
