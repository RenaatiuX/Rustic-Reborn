package com.rena.rustic.common.world;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.world.gen.RusticTreeGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RusticReborn.MOD_ID)
public class RusticWorldEvent {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        RusticTreeGeneration.generateTrees(event);
    }

}
