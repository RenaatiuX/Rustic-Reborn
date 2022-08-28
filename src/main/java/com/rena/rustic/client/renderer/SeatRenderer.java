package com.rena.rustic.client.renderer;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.entity.SittableEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SeatRenderer extends EntityRenderer<SittableEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(RusticReborn.MOD_ID, "");

    public SeatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SittableEntity entity) {
        return TEXTURE;
    }
}
