package com.rena.rustic.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rena.rustic.RusticReborn;
import com.rena.rustic.client.model.CabinetModel;
import com.rena.rustic.client.model.DoubleCabinetModel;
import com.rena.rustic.client.model.LayerDefinitions;
import com.rena.rustic.common.block.BlockCabinet;
import com.rena.rustic.common.blockentity.CabinetTileEntity;
import com.rena.rustic.core.BlockInit;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CabinetRenderer implements BlockEntityRenderer<CabinetTileEntity> {
    private final BlockEntityRendererProvider.Context ctx;


    public CabinetRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(CabinetTileEntity te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (te != null && te.getLevel().getBlockState(te.getBlockPos()).is(BlockInit.CABINET.get()) && (!te.isDouble() || te.getBlockState().getValue(BlockCabinet.CONNECTION) == BlockCabinet.ConnectionType.BOTTOM)) {
            pPoseStack.pushPose();
            float rotation = 0f;
            Model cabinetModel = getModel(te);
            switch (te.getLevel().getBlockState(te.getBlockPos()).getValue(BlockCabinet.HORIZONTAL_FACING)) {
                case WEST:
                    rotation = 90;
                    break;
                case NORTH:
                    rotation = 0;
                    break;
                case EAST:
                    rotation = 270;
                    break;
                case SOUTH:
                    rotation = 180;
                default:
                    break;
            }
            float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * pPartialTick;
            f = 1.0F - f;
            f = 1.0F - f * f * f;
            if (te.isDouble()) {
                ((DoubleCabinetModel) cabinetModel).rotate(te.isMirror() ? -(f * ((float) Math.PI / 2F)) : (f * ((float) Math.PI / 2F)));
            } else {
                ((CabinetModel) cabinetModel).rotate(te.isMirror() ? -(f * ((float) Math.PI / 2F)) : (f * ((float) Math.PI / 2F)));
            }
            pPoseStack.translate(0.5f, -0.5f, 0.5f);
            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
            cabinetModel.renderToBuffer(pPoseStack, pBufferSource.getBuffer(cabinetModel.renderType(getTextureLocation(te))), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 0f);

            pPoseStack.popPose();
        }
    }

    private ResourceLocation getTextureLocation(CabinetTileEntity te) {
        return te.isDouble() ? RusticReborn.modLoc("textures/models/cabinet_double_color.png") : RusticReborn.modLoc("textures/models/cabinet_color.png");
    }

    private Model getModel(CabinetTileEntity te) {
        if (!te.isDouble() && te.isMirror()) {
            return new CabinetModel(ctx.bakeLayer(LayerDefinitions.CABINET_MIRROR));
        } else if (te.isDouble()) {
            if (te.isMirror())
                return new DoubleCabinetModel(ctx.bakeLayer(LayerDefinitions.DOUBLE_CABINET_MIRROR));
            else
                return new DoubleCabinetModel(ctx.bakeLayer(LayerDefinitions.DOUBLE_CABINET));
        }

        return new CabinetModel(ctx.bakeLayer(LayerDefinitions.CABINET));
    }
}
