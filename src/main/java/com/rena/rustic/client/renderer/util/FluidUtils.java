package com.rena.rustic.client.renderer.util;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.rena.rustic.RusticReborn;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Map;

public class FluidUtils {

    public static final Map<FluidStack, Int2ObjectMap<Model3D>> CACHED_FLUIDS = Maps.newHashMap();
    public static final int STAGES = 1400;

    public static float getScale(IFluidHandler tank) {
        int amount = 0, cap = 0;
        for (int i = 0; i < tank.getTanks(); i++) {
            amount += tank.getFluidInTank(i).getAmount();
            cap += tank.getTankCapacity(i);
        }
        return getScale(amount, cap, cap > 0);
    }

    public static float getScale(int stored, int capacity, boolean empty) {
        float targetScale = (float) stored / capacity;
        return targetScale;
    }

    public static Model3D getFluidModel(FluidStack fluid, int stage) {
        if (CACHED_FLUIDS.containsKey(fluid) && CACHED_FLUIDS.get(fluid).containsKey(stage)) {
            return CACHED_FLUIDS.get(fluid).get(stage);
        }
        Model3D model = new Model3D();
        model.setTexture(getFluidTexture(fluid, FluidType.STILL));
        if (fluid.getFluid().getAttributes().getStillTexture(fluid) != null) {
            double sideSpacing = 0.00625;
            double belowSpacing = 0.0625 / 4;
            double topSpacing = belowSpacing;
            model.minX = sideSpacing;
            model.minY = belowSpacing;
            model.minZ = sideSpacing;
            model.maxX = 1 - sideSpacing;
            model.maxY = 1 - topSpacing;
            model.maxZ = 1 - sideSpacing;
        }
        if (CACHED_FLUIDS.containsKey(fluid)) {
            CACHED_FLUIDS.get(fluid).put(stage, model);
        }
        else {
            Int2ObjectMap<Model3D> map = new Int2ObjectOpenHashMap<>();
            map.put(stage, model);
            CACHED_FLUIDS.put(fluid, map);
        }
        return model;
    }


    public static TextureAtlasSprite getFluidTexture(FluidStack fluidStack, FluidType type) {
        Fluid fluid = fluidStack.getFluid();
        ResourceLocation spriteLocation;
        if (type == FluidType.STILL) {
            spriteLocation = fluid.getAttributes().getStillTexture(fluidStack);
        }
        else {
            spriteLocation = fluid.getAttributes().getFlowingTexture(fluidStack);
        }
        return getSprite(spriteLocation);
    }

    public static TextureAtlasSprite getSprite(ResourceLocation spriteLocation) {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(spriteLocation);
    }

    public enum FluidType {
        STILL, FLOWING
    }
}
