package com.rena.rustic.client;

import com.rena.rustic.client.renderer.CabinetRenderer;
import com.rena.rustic.client.renderer.CrushingTubTileEntityRenderer;
import com.rena.rustic.client.screens.ApiaryScreen;
import com.rena.rustic.common.item.VaseItem;
import com.rena.rustic.core.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void setRenderLayers(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(FluidInit.OLIVE_OIL_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.OLIVE_OIL_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.OLIVE_OIL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.IRONBERRY_JUICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.IRONBERRY_JUICE_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.IRONBERRY_JUICE_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.APPLE_JUICE_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.APPLE_JUICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.APPLE_JUICE_FLOWING.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.CABINET.get(), RenderType.translucent());

        //ItemBlockRenderTypes.setRenderLayer(BlockInit.CRUSHING_TUB.get(), RenderType.cutout());

        MenuScreens.register(ContainerInit.APIARY_CONTAINER.get(), ApiaryScreen::new);

        BlockEntityRenderers.register(BlockEntityInit.CRUSHING_TUB_TILE_ENTITY.get(), CrushingTubTileEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.CABINET.get(), CabinetRenderer::new);

        ItemProperties.register(BlockInit.VASE.get().asItem(), new ResourceLocation("variant"), (stack, clientWorld, living, another) -> VaseItem.getVariant(stack));

        ItemBlockRenderTypes.setRenderLayer(BlockInit.GARGOYLE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.WILDBERRIES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.IRONWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.OLIVE_SAPLING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.GRAPE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.APPLE_LEAVES.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.TOMATO_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CHILI_CROP.get(), RenderType.cutout());
    }

}
