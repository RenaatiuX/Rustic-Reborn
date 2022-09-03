package com.rena.rustic.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.model.generators.ModelProvider;

import java.util.function.Function;

public class CabinetModel extends Model {

    final ModelPart bottom;
    final ModelPart top;
    final ModelPart back;
    final ModelPart right;
    final ModelPart left;
    public final ModelPart door;
    final ModelPart handle;

    public CabinetModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.bottom = root.getChild("bottom");
        this.top = root.getChild("top");
        this.back = root.getChild("back");
        this.right = root.getChild("right");
        this.left = root.getChild("left");
        this.door = root.getChild("door");
        this.handle = door.getChild("handle");
    }

    public static LayerDefinition createLayer(boolean mirror){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        root.addOrReplaceChild("bottom", CubeListBuilder.create().mirror().addBox(0F, 0F, 0F, 16, 1, 16), PartPose.offset(-8F, 23F, -8F));
        root.addOrReplaceChild("top", CubeListBuilder.create().mirror().addBox(0F, 0F, 0F, 16, 1, 16), PartPose.offset(-8F, 8F, -8F));
        root.addOrReplaceChild("back", CubeListBuilder.create().mirror().addBox(0F, 0F, 0F, 16, 14, 1), PartPose.offset(-8F, 9F, 7F));
        root.addOrReplaceChild("right", CubeListBuilder.create().mirror().addBox(0F, 0F, 0F, 1, 14, 15), PartPose.offset(-8F, 9F, -8F));
        root.addOrReplaceChild("left", CubeListBuilder.create().mirror().addBox(0F, 0F, 0F, 1, 30, 15 ), PartPose.offset(7F, -7F, -8F));
        PartDefinition door = root.addOrReplaceChild("door", CubeListBuilder.create().mirror().addBox(mirror ? -14F : 0F, -15F, -0.5F, 14, 30, 1), PartPose.offset((mirror) ? 7F : -7F, 8F, -6.5F));
        door.addOrReplaceChild("handle", CubeListBuilder.create().mirror().addBox((mirror) ? -13F : 12F, -1F, -1.5F, 1, 2, 1), PartPose.offset((mirror) ? 7F : -7F, 8F, -6.5F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {

    }
}
