package com.besson.tutorial.world.gen;

public class ModWorldGeneration {
    public static void registerWorldGeneration() {
        ModTreeGeneration.registerTrees();
        ModFlowerGeneration.registerFlowers();
        ModOreGeneration.registerOres();
    }
}
