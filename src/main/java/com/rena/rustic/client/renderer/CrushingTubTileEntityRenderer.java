package com.rena.rustic.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.rena.rustic.RusticReborn;
import com.rena.rustic.client.renderer.util.FluidRenderTypes;
import com.rena.rustic.client.renderer.util.FluidUtils;
import com.rena.rustic.client.renderer.util.RenderResizableCuboid;
import com.rena.rustic.client.renderer.util.RenderUtils;
import com.rena.rustic.common.block_entity.CrushingTubTileEntitiy;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;
import java.util.logging.Logger;

public class CrushingTubTileEntityRenderer implements BlockEntityRenderer<CrushingTubTileEntitiy> {

    private final BlockEntityRendererProvider.Context ctx;
    private float minU, minV, maxU, maxV, diffU, diffV;

    public CrushingTubTileEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public void render(CrushingTubTileEntitiy te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (!te.getItem(0).isEmpty()) {
            pPoseStack.pushPose();
            drawFluid(te, pPoseStack, pBufferSource, 1F * 0.0625F, 0F * 0.0625F, 1F * 0.0625F, 15F * 0.0625F, 7F * 0.0625F, 15F * 0.0625F, pPackedLight);
            pPoseStack.popPose();
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
                Minecraft.getInstance().getItemRenderer().renderStatic(Minecraft.getInstance().player, stack, ItemTransforms.TransformType.NONE, false, pPoseStack, pBufferSource, Minecraft.getInstance().level, pPackedOverlay, pPackedOverlay, pPackedOverlay);
                pPoseStack.popPose();
            }
        }
    }

    private void drawFluid(CrushingTubTileEntitiy te, PoseStack poseStack, MultiBufferSource source, float x, float y, float z, float width, float height, float depth, int light) {
        Fluid fluid = te.getTank().getFluid().getFluid();
        if (fluid == Fluids.EMPTY)
            return;

        TextureAtlasSprite sprite = ForgeHooksClient.getFluidSprites(te.getLevel(), te.getBlockPos(), fluid.defaultFluidState())[0];

        float minU = sprite.getU0();
        float maxU = Math.min(minU + (sprite.getU1() - minU) * depth, sprite.getU1());
        float minV = sprite.getV0();
        float maxV = Math.min(minV + (sprite.getV1() - minV) * width, sprite.getV1());
        int waterColor = fluid.getAttributes().getColor(te.getLevel(), te.getBlockPos());
        float red = (float) (waterColor >> 16 & 255) / 255.0F;
        float green = (float) (waterColor >> 8 & 255) / 255.0F;
        float blue = (float) (waterColor & 255) / 255.0F;

        height *= ((double) te.getTank().getFluidAmount() / (double) te.getTank().getCapacity());

        VertexConsumer consumer = source.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        consumer.vertex(matrix, x, y + height, z).color(red, green, blue, 1.0F).uv(maxU, minV).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(matrix, x, y + height, z + depth).color(red, green, blue, 1.0F).uv(minU, minV).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(matrix, x + width, y + height, z + depth).color(red, green, blue, 1.0F).uv(minU, maxV).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(matrix, x + width, y + height, z).color(red, green, blue, 1.0F).uv(maxU, maxV).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
    }
}
