package com.rena.rustic.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rena.rustic.common.blockentity.CabinetTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CabinetRenderer implements BlockEntityRenderer<CabinetTileEntity> {
    private final BlockEntityRendererProvider.Context ctx;

    public CabinetRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }
    @Override
    public void render(CabinetTileEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }
}
