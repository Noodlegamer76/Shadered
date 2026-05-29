package com.noodlegamer76.shadered.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.client.assimp.McModel;
import com.noodlegamer76.shadered.client.assimp.load.AssimpModels;
import com.noodlegamer76.shadered.client.renderer.assimp.AssimpRenderer;
import com.noodlegamer76.shadered.client.renderer.assimp.RenderableModel;
import com.noodlegamer76.shadered.entity.block.MaxwellEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.joml.Matrix4f;

public class MaxwellItemRenderer extends BlockEntityWithoutLevelRenderer {

    public MaxwellItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        McModel model = AssimpModels.getModel(
                ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "models/complex/maxwell.glb")
        );

        if (model == null) return;

        RenderableModel renderableModel = new RenderableModel();

        for (AssimpModel assimpModel : model.getModels()) {
            renderableModel.add(assimpModel);
        }

        boolean gui = displayContext == ItemDisplayContext.GUI;

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);

        if (displayContext == ItemDisplayContext.GUI) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0,
                    ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/maxwell.png"));

            PoseStack.Pose pose = poseStack.last();
            Matrix4f mat = pose.pose();

            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            float size = 1f;

            builder.addVertex(mat, -size, size, 0).setUv(0, 0);
            builder.addVertex(mat, -size, -size, 0).setUv(0, 1);
            builder.addVertex(mat, size, -size, 0).setUv(1, 1);
            builder.addVertex(mat, size, size, 0).setUv(1, 0);

            BufferUploader.drawWithShader(builder.build());

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
        }
        else {
            poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            poseStack.scale(0.025f, 0.025f, 0.025f);

            renderableModel.modelMatrix = new Matrix4f(poseStack.last().pose());

            AssimpRenderer.getInstance().renderDirect(renderableModel, poseStack, gui);
        }

        poseStack.popPose();
    }
}
