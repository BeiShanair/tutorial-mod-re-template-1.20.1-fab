package com.besson.tutorial;

import com.besson.tutorial.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TutorialModReDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// 最后，在所有数据生成类写完之后，要在这里调用我们的数据生成类
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModEnLangProvider::new);
		pack.addProvider(ModItemTagsProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelsProvider::new);
		pack.addProvider(ModRecipesProvider::new);
		pack.addProvider(ModZhLangProvider::new);
		pack.addProvider(ModPointTagProvider::new);
	}
}
