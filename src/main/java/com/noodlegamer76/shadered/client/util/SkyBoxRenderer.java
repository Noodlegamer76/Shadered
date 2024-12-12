package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class SkyBoxRenderer {
    public static final ResourceLocation TEXTURE2 = new ResourceLocation(ShaderedMod.MODID, "textures/environment/layer1/skybox2");
    public static final ResourceLocation TEXTURE_EEE = new ResourceLocation(ShaderedMod.MODID, "textures/environment/layer1/eee");

    public static void renderBlockSkybox(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                              ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                              ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        for(int i = 0; i < 6; ++i) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees((ticks + partialTick) * speed));
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
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.vertex(matrix4f, -far, -far, -far).uv(0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, -far, -far, far).uv(0.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, far, -far, far).uv(1.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, far, -far, -far).uv(1.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
            tesselator.end();
            poseStack.popPose();
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
