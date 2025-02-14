package com.besson.tutorial.block;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.custom.SimpleCabinetBE;
import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModBlockEntities {
    // 注册实体（虽然我们这里归类给他归在了方块实体中）
    public static final EntityType<SeatEntity> SEAT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(TutorialModRe.MOD_ID, "seat"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SeatEntity::new).build());

    public static final BlockEntityType<SimpleCabinetBE> SIMPLE_CABINET = create("simple_cabinet",
            BlockEntityType.Builder.create(SimpleCabinetBE::new, ModBlocks.SIMPLE_CABINET));

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new  Identifier(TutorialModRe.MOD_ID, id), builder.build(type));
    }

    public static void registerModBlockEntities() {

    }
}
