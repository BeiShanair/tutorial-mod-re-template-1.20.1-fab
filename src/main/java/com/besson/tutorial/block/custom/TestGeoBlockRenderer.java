package com.besson.tutorial.block.custom;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TestGeoBlockRenderer extends GeoBlockRenderer<TestGeoEntity> {
    public TestGeoBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new TestGeoBlockModel());
    }
}
