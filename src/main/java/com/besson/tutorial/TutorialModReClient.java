package com.besson.tutorial;

import com.besson.tutorial.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	}
}