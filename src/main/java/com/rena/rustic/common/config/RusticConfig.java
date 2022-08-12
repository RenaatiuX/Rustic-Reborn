package com.rena.rustic.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RusticConfig {

    public static ForgeConfigSpec.IntValue BEE_GROWTH_MULTIPLIER, BEE_REPRODUCTION_MULTIPLIER, BEE_HONEYCOMB_MULTIPLIER;

    public static final ForgeConfigSpec init(){
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        apiaryConfig(builder);
        return builder.build();
    }

    private static final void apiaryConfig(ForgeConfigSpec.Builder builder){
        builder.push("Apiary");
        BEE_GROWTH_MULTIPLIER = builder.defineInRange("Bee Growth Multiplier", 1, 0, 10);
        BEE_REPRODUCTION_MULTIPLIER = builder.defineInRange("Bee Production Multiplier", 1, 0, 10);
        BEE_HONEYCOMB_MULTIPLIER = builder.defineInRange("Bee Honeycomb Multiplier", 1, 0, 10);
        builder.pop();

    }
}
