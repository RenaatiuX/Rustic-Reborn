package com.rena.rustic.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.config.RusticConfig;
import com.rena.rustic.common.container.ApiaryContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ApiaryScreen extends AbstractContainerScreen<ApiaryContainer> {
    public static final ResourceLocation TEXTURE = RusticReborn.modLoc("textures/gui/apiary.png");

    public ApiaryScreen(ApiaryContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(pPoseStack, middleX, middleY, 0, 0, 176, 166);
    }
}
