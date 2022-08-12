package com.rena.rustic.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3f;
import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block_entity.CrushingTubTileEntitiy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.Random;

public class CrushingTubTileEntityRenderer implements BlockEntityRenderer<CrushingTubTileEntitiy> {

    private final BlockEntityRendererProvider.Context ctx;

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
                Minecraft.getInstance().getItemRenderer().renderStatic(Minecraft.getInstance().player, stack, ItemTransforms.TransformType.NONE, false, pPoseStack, pBufferSource, Minecraft.getInstance().level, pPackedOverlay, pPackedOverlay, pPackedOverlay);
                pPoseStack.popPose();
            }
        }
        if (te.getTank().getFluidAmount() > 0) {
            pPoseStack.pushPose();
            FluidStack stack = te.getTank().getFluid();
            int amount = stack.getAmount();
            int capacity = te.getTank().getCapacity();
            ResourceLocation stillTexture = stack.getFluid().getAttributes().getStillTexture(stack);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
            int c = stack.getFluid().getAttributes().getColor();
            int blue = c & 0xFF;
            int green = (c >> 8) & 0xFF;
            int red = (c >> 16) & 0xFF;
            int a = (c >> 24) & 0xFF;

            float minU = sprite.getU0();
            float maxU = sprite.getU1();
            float minV = sprite.getV0();
            float maxV = sprite.getV1();


            int i = Minecraft.getInstance().level.getLightEngine().getRawBrightness(te.getBlockPos(), stack.getFluid().getAttributes().getLuminosity());
            int lightx = i >> 0x10 & 0xFFFF;
            int lighty = i & 0xFFFF;
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            RenderSystem.setShaderColor(red, green, blue, a);
            VertexConsumer consumer = pBufferSource.getBuffer(RenderType.translucent());
            consumer.vertex(pPoseStack.last().pose(), 0.0625f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.0625f).uv(minU, minV).normal(1, 0,0).uv2(lightx, lighty).color(red, green, blue, a);
            consumer.vertex(pPoseStack.last().pose(), 0.9375f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.0625f).uv(maxU, minV).normal(1, 0,0).uv2(lightx, lighty).color(red, green, blue, a);
            consumer.vertex(pPoseStack.last().pose(), 0.9375f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.9375f).uv(maxU, maxV).normal(1, 0,0).uv2(lightx, lighty).color(red, green, blue, a);
            consumer.vertex(pPoseStack.last().pose(), 0.0625f, 0.0625f + 0.5f * ((float) amount / (float) capacity), 0.9375f).uv(minU, maxV).normal(1, 0,0).uv2(lightx, lighty).color(red, green, blue, a);
            RenderSystem.disableBlend();
            pPoseStack.popPose();
        }
    }
}
