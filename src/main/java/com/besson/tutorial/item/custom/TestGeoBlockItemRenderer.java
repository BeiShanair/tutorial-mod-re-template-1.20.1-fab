package com.besson.tutorial.item.custom;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TestGeoBlockItemRenderer extends GeoItemRenderer<TestGeoBlockItem> {
    public TestGeoBlockItemRenderer() {
        super(new TestGeoBlockItemModel());
    }
}
