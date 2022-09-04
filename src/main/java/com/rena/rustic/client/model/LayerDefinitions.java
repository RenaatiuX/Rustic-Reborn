package com.rena.rustic.client.model;

import com.rena.rustic.RusticReborn;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RusticReborn.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LayerDefinitions {

    public static final ModelLayerLocation CABINET = register("cabinet");
    public static final ModelLayerLocation CABINET_MIRROR = register("cabinet_mirror");
    public static final ModelLayerLocation DOUBLE_CABINET = register("double_cabinet");
    public static final ModelLayerLocation DOUBLE_CABINET_MIRROR = register("double_cabinet_mirror");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(RusticReborn.MOD_ID, p_171301_), p_171302_);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CABINET, () -> CabinetModel.createLayer(false));
        event.registerLayerDefinition(CABINET_MIRROR, () -> CabinetModel.createLayer(true));
        event.registerLayerDefinition(DOUBLE_CABINET, () -> DoubleCabinetModel.createLayer(false));
        event.registerLayerDefinition(DOUBLE_CABINET_MIRROR, () -> DoubleCabinetModel.createLayer(true));
    }
}
