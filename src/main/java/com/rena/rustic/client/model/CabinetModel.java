package com.rena.rustic.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class CabinetModel extends Model {

    final ModelPart bottom;
    final ModelPart top;
    final ModelPart back;
    final ModelPart right;
    final ModelPart left;
    final ModelPart door;
    final ModelPart handle;

    public CabinetModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.bottom = root.getChild("bottom");
        this.top = root.getChild("top");
        this.back = root.getChild("back");
        this.right = root.getChild("right");
        this.left = root.getChild("left");
        this.door = root.getChild("door");
        this.handle = root.getChild("handle");
    }

    public static LayerDefinition createLayer(boolean mirror){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        root.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0,0).addBox(0F, 0F, 0F, 16, 1, 16), PartPose.offset(-8F, 23F, -8F));
        root.addOrReplaceChild("top", CubeListBuilder.create().texOffs(64, 0).addBox(0F, 0F, 0F, 16, 1, 16), PartPose.offset(-8F, 8F, -8F));
        root.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 17).addBox(0F, 0F, 0F, 16, 14, 1), PartPose.offset(-8F, 9F, 7F));
        root.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 32).addBox(0F, 0F, 0F, 1, 14, 15), PartPose.offset(-8F, 9F, -8F));
        root.addOrReplaceChild("left", CubeListBuilder.create().texOffs(32, 32).addBox(0F, 0F, 0F, 1, 14, 15), PartPose.offset(7F, 9F, -8F));
        root.addOrReplaceChild("door", CubeListBuilder.create().texOffs(64, 17).addBox((mirror) ? -14F : 0F, -7F, -0.5F, 14, 14, 1), PartPose.offset((mirror) ? 7F : -7F, 16F, -6.5F));
        root.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(94, 17).addBox(mirror ? -13F : 12F, -1F, -1.5F, 1, 2, 1), PartPose.offset((mirror) ? 7F : -7F, 16F, -6.5F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        bottom.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        top.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        back.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        right.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        left.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        door.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        handle.render(pPoseStack, pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    public void rotate(float angleY){
        this.door.yRot = angleY;
        this.handle.yRot = angleY;
    }
}
