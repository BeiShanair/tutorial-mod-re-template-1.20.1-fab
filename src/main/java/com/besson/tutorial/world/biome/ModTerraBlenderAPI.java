package com.besson.tutorial.world.biome;

import com.besson.tutorial.TutorialModRe;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerraBlenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverWorldRegion(new Identifier(TutorialModRe.MOD_ID, "overworld"),  4));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, TutorialModRe.MOD_ID, ModMaterialRules.makeRules());
    }
}
