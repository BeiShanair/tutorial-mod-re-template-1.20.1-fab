package com.besson.tutorial.item.custom;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TestGeoBlockItemModel extends GeoModel<TestGeoBlockItem> {
    @Override
    public Identifier getModelResource(TestGeoBlockItem animatable) {
        return new Identifier(TutorialModRe.MOD_ID, "geo/test_geo_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(TestGeoBlockItem animatable) {
        return new Identifier(TutorialModRe.MOD_ID, "textures/block/test_geo_block.png");
    }

    @Override
    public Identifier getAnimationResource(TestGeoBlockItem animatable) {
        return new Identifier(TutorialModRe.MOD_ID, "animations/test_geo_block.animation.json");
    }
}
