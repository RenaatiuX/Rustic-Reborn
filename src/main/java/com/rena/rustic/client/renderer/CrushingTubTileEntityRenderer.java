package com.rena.rustic.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3f;
import com.rena.rustic.client.renderer.util.FluidRenderTypes;
import com.rena.rustic.client.renderer.util.FluidUtils;
import com.rena.rustic.client.renderer.util.RenderResizableCuboid;
import com.rena.rustic.client.renderer.util.RenderUtils;
import com.rena.rustic.common.blockentity.CrushingTubTileEntitiy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class CrushingTubTileEntityRenderer implements BlockEntityRenderer<CrushingTubTileEntitiy> {

    private final BlockEntityRendererProvider.Context ctx;
    private float minU, minV, maxU, maxV, diffU, diffV;

    public CrushingTubTileEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public void render(CrushingTubTileEntitiy te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (!te.getItem(0).isEmpty()) {
            ItemStack stack = te.getItem(0);
            int itemCount = (int) Math.ceil((stack.getCount()) / 8.0);
            Random rand = new Random(10);
            for (int i = 0; i < itemCount; i++) {
                pPoseStack.pushPose();
                pPoseStack.translate(0, 0.062 + (i * 0.0625), 0);
                pPoseStack.translate(0.5, 0, 0.5);
                pPoseStack.mulPose(Vector3f.YN.rotationDegrees(rand.nextFloat() * 360.0f));
                pPoseStack.mulPose(Vector3f.XN.rotationDegrees(90));
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, 0);
                pPoseStack.popPose();
            }
        }
        if (te.getTank().getFluidAmount() > 0) {
            FluidStack fluid = te.getTank().getFluidInTank(0);
            if (fluid.isEmpty()) {
                return;
            }
            pPoseStack.pushPose();
            int capacity = te.getCapacity();
            int amount = te.getAmount();
            /*
            int c = fluid.getFluid().getAttributes().getColor();
            int blue = c & 0xFF;
            int green = (c >> 8) & 0xFF;
            int red = (c >> 16) & 0xFF;
            int a = (c >> 24) & 0xFF;
            TextureAtlasSprite sprite = FluidUtils.getFluidTexture(fluid, FluidUtils.FluidType.STILL);
            if (sprite == null) return;
            diffU = maxU - minU;
            diffV = maxV - minV;
            minU = sprite.getU0() + diffU * 0.0625f;
            maxU = sprite.getU1() - diffU * 0.0625f;
            minV = sprite.getV0() + diffV * 0.0625f;
            maxV = sprite.getV1() - diffV * 0.0625f;
            int light = RenderUtils.calculateGlowLight(pPackedLight, fluid);
            pPoseStack.translate(0, 1, 0);
            Matrix4f calc = pPoseStack.last().pose();
            VertexConsumer buffer = pBufferSource.getBuffer(RenderType.solid());
            buffer.vertex(calc, 0.0625f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.0625f).color(red, green, blue, a).uv(minU, minV).uv2(light).normal(0f, -1f, 0f).overlayCoords(pPackedOverlay).endVertex();
            buffer.vertex(calc, 0.9375f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.0625f).color(red, green, blue, a).uv(maxU, minV).uv2(light).normal(0f, -1f, 0f).overlayCoords(pPackedOverlay).endVertex();
            buffer.vertex(calc, 0.9375f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.9375f).color(red, green, blue, a).uv(maxU, maxV).uv2(light).normal(0f, -1f, 0f).overlayCoords(pPackedOverlay).endVertex();
            buffer.vertex(calc, 0.0625f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.9375f).color(red, green, blue, a).uv(minU, maxV).uv2(light).normal(0f, -1f, 0f).overlayCoords(pPackedOverlay).endVertex();
            pPoseStack.popPose();*/
            RenderSystem.enableBlend();
            VertexConsumer buffer = pBufferSource.getBuffer(FluidRenderTypes.RESIZABLE);
            pPoseStack.scale(1F, 0.5f*((float)amount/(float)capacity), 1F);
            RenderResizableCuboid.INSTANCE.renderCubeTop(FluidUtils.getFluidModel(fluid, FluidUtils.STAGES - 1),
                    pPoseStack, buffer, RenderUtils.getColorARGB(fluid, 0.1F),
                    RenderUtils.calculateGlowLight(pPackedLight, fluid), pPackedOverlay);
            RenderSystem.disableBlend();
            pPoseStack.popPose();
        }
    }
}
