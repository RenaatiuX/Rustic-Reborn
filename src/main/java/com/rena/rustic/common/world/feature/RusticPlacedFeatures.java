package com.rena.rustic.common.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class RusticPlacedFeatures {

    public static final Holder<PlacedFeature> IRONWOOD_PLACED = PlacementUtils.register("ironwood_placed",
            RusticConfiguredFeatures.IRONWOOD_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(3, 0.1f, 2)));

}
