package com.besson.tutorial;

import com.besson.tutorial.block.ModBlockEntities;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.ModFluids;
import com.besson.tutorial.entity.ModBoats;
import com.besson.tutorial.item.ModItemGroups;
import com.besson.tutorial.item.ModItems;
import com.besson.tutorial.sound.ModSoundEvents;
import com.besson.tutorial.util.ModCustomTrades;
import com.besson.tutorial.villager.ModVillagers;
import com.besson.tutorial.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class TutorialModRe implements ModInitializer {
	public static final String MOD_ID = "tutorial-mod-re";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		// 在主类的onInitialize方法中调用我们的初始化方法
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModBlocks.registerModBlocks();

		// 注册自定义交易
		ModCustomTrades.registerCustomTrades();

		// 注册村民
		ModVillagers.registerVillagers();

		ModSoundEvents.registerSounds();

		ModBlockEntities.registerModBlockEntities();

		ModFluids.registerModFluids();

		ModBoats.registerBoats();
		ModWorldGeneration.registerWorldGeneration();

		GeckoLib.initialize();
		// 燃料最快捷的注册方法是用Fabric的API实现，参数分别为燃料和燃烧时间（tick）
//		FuelRegistry.INSTANCE.add(ModItems.ANTHRACITE, 600);

		// 原木可被削皮
		// 这里使用Fabric API中的StrippableBlockRegistry注册可以削皮的原木和木头
		StrippableBlockRegistry.register(ModBlocks.ICE_ETHER_LOG, ModBlocks.STRIPPED_ICE_ETHER_LOG);
		StrippableBlockRegistry.register(ModBlocks.ICE_ETHER_WOOD, ModBlocks.STRIPPED_ICE_ETHER_WOOD);

		// 原木和木头可以被点燃，也能够燃烧
		// 见原版的FireBlock
		// 当然，要作为燃料要将其加入Tag
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ICE_ETHER_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ICE_ETHER_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_ICE_ETHER_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_ICE_ETHER_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ICE_ETHER_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ICE_ETHER_LEAVES, 30, 60);
	}
}