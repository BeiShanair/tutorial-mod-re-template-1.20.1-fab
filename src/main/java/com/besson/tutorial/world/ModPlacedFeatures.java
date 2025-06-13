package com.besson.tutorial.world;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ICE_ETHER_TREE_PLACED_KEY = ModPlacedFeatures.of("ice_ether_tree_placed");
    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(TutorialModRe.MOD_ID, id));
    }

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        PlacedFeatures.register(featureRegisterable, ICE_ETHER_TREE_PLACED_KEY,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_ETHER_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f, 2),
                        ModBlocks.ICE_ETHER_TREE_SAPLING
                ));
    }
}
