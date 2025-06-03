package com.besson.tutorial;

import com.besson.tutorial.block.ModBlockEntities;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.ModFluids;
import com.besson.tutorial.block.SeatRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class TutorialModReClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		/* 对于有着透明通道材质的方块，我们要为其设置渲染层，否则透明的区域会变成黑色
		   getCutout为全透明材质使用，如玻璃，玻璃板
		   getTranslucent为半透明材质使用，如各类染色玻璃及玻璃板
		   另外，这个也可以通过Mixin原版的RenderLayer类实现
		 */
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_TRAPDOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_BLOCK, RenderLayer.getTranslucent());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());

		EntityRendererRegistry.register(ModBlockEntities.SEAT, SeatRenderer::new);

		// 为流体设置颜色
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OIL, ModFluids.FLOWING_OIL,
				new SimpleFluidRenderHandler(
						new Identifier("minecraft:block/water_still"),
						new Identifier("minecraft:block/water_flow"),
						0x42413b
				));
		// 设置渲染层
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OIL, ModFluids.FLOWING_OIL);
	}
}