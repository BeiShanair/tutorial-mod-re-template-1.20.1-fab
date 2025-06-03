package com.besson.tutorial.block;

import com.besson.tutorial.TutorialModRe;
import com.besson.tutorial.block.custom.OilFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static final FlowableFluid OIL = register("oil", new OilFluid.Still());
    public static final FlowableFluid FLOWING_OIL = register("flowing_oil", new OilFluid.Flowing());

    // 注册方法，改自原版方法
    private static <T extends Fluid> T register(String name, T fluid) {
        return (T) Registry.register(Registries.FLUID, new Identifier(TutorialModRe.MOD_ID, name), fluid);
    }

    static {
        for (Fluid fluid : Registries.FLUID) {
            for (FluidState fluidState : fluid.getStateManager().getStates()) {
                Fluid.STATE_IDS.add(fluidState);
            }
        }
    }
    // 初始化注册方法
    public static void registerModFluids() {

    }
}
