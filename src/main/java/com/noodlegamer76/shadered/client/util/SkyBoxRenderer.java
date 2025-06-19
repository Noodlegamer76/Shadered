package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.util.Quantifier;

import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_SKY_LOCATION;

public class SkyBoxRenderer {

    public static void renderBlockSkybox(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                              ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                              ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Quaternionf quaternionf = new Quaternionf(camera.rotation()).invert();


        for(int i = 0; i < 6; ++i) {
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees((ticks + partialTick) * speed));
            poseStack.mulPose(quaternionf);
            if (i == 0) {
                RenderSystem.setShaderTexture(0, frontTexture);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 1) {
                RenderSystem.setShaderTexture(0, rightTexture);
                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(-90));
            }

            if (i == 2) {
                RenderSystem.setShaderTexture(0, leftTexture);
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
            }

            if (i == 3) {
                RenderSystem.setShaderTexture(0, backTexture);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            }

            if (i == 4) {
                RenderSystem.setShaderTexture(0, bottomTexture);
                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 5) {
                RenderSystem.setShaderTexture(0, topTexture);
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }
            float far = (float) Minecraft.getInstance().gameRenderer.getRenderDistance();
            Matrix4f matrix4f = poseStack.last().pose();
            bufferBuilder.addVertex(matrix4f, -far, -far, -far).setUv(0.0F, 0.0F);
            bufferBuilder.addVertex(matrix4f, -far, -far, far).setUv(0.0F, 1.0F);
            bufferBuilder.addVertex(matrix4f, far, -far, far).setUv(1.0F, 1.0F);
            bufferBuilder.addVertex(matrix4f, far, -far, -far).setUv(1.0F, 0.0F);
            poseStack.popPose();

            MeshData data = bufferBuilder.build();

            if (data != null) {
                BufferUploader.drawWithShader(data);
            }
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    public static void renderEndSky(PoseStack pPoseStack) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
        Quaternionf rotation = new Quaternionf(Minecraft.getInstance().gameRenderer.getMainCamera().rotation()).invert();

        for(int i = 0; i < 6; ++i) {
            pPoseStack.pushPose();
            pPoseStack.mulPose(rotation);
            if (i == 1) {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = pPoseStack.last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(-14145496);
            bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(-14145496);
            bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(-14145496);
            bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(-14145496);
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            pPoseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    public static void renderBlockSkybox(PoseStack poseStack, ResourceLocation folder) {
        SkyBoxRenderer.renderBlockSkybox(poseStack, 0, 0, 255, 0,
                folder.withSuffix("/front.png"),
                folder.withSuffix("/back.png"),
                folder.withSuffix("/left.png"),
                folder.withSuffix("/right.png"),
                folder.withSuffix("/top.png"),
                folder.withSuffix("/bottom.png")
        );
    }


}
