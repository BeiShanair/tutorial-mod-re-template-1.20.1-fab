package com.besson.tutorial.block.custom;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TestGeoBlockModel extends GeoModel<TestGeoEntity> {
    @Override
    public Identifier getModelResource(TestGeoEntity testGeoEntity) {
        return new Identifier(TutorialModRe.MOD_ID, "geo/test_geo_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(TestGeoEntity testGeoEntity) {
        return new Identifier(TutorialModRe.MOD_ID, "textures/block/test_geo_block.png");
    }

    @Override
    public Identifier getAnimationResource(TestGeoEntity testGeoEntity) {
        return new Identifier(TutorialModRe.MOD_ID, "animations/test_geo_block.animation.json");
    }
}
