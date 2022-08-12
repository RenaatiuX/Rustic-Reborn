package com.rena.rustic.client;

import com.rena.rustic.client.renderer.CrushingTubTileEntityRenderer;
import com.rena.rustic.client.screens.ApiaryScreen;
import com.rena.rustic.core.BlockEntityInit;
import com.rena.rustic.core.BlockInit;
import com.rena.rustic.core.ContainerInit;
import com.rena.rustic.core.FluidInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void setRenderLayers(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(FluidInit.OLIVE_OIL_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.OLIVE_OIL_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.OLIVE_OIL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.IRONBERRY_JUICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.IRONBERRY_JUICE_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.IRONBERRY_JUICE_FLOWING.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockInit.CRUSHING_TUB.get(), RenderType.translucent());

        MenuScreens.register(ContainerInit.APIARY_CONTAINER.get(), ApiaryScreen::new);

        BlockEntityRenderers.register(BlockEntityInit.CRUSHING_TUB_TILE_ENTITY.get(), CrushingTubTileEntityRenderer::new);
    }
}
