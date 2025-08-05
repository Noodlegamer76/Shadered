package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_SKY_LOCATION;

public class SkyBoxRenderer {
    public static final ResourceLocation TEXTURE2 = new ResourceLocation(ShaderedMod.MODID, "textures/environment/layer1/skybox2");
    public static final ResourceLocation TEXTURE_EEE = new ResourceLocation(ShaderedMod.MODID, "textures/environment/layer1/eee");

    //this stuff is a nightmare
    public static void renderBlockSkybox(PoseStack poseStack, int ticks, float partialTick, int alpha, float speed,
                                         ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                                         ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        float far = Minecraft.getInstance().gameRenderer.getRenderDistance();
        float rotation = (ticks + partialTick) * speed;

        poseStack.pushPose();

        poseStack.mulPose(Axis.YN.rotationDegrees(rotation));


        // Front face (+Z)
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, frontTexture);
        drawQuad(bufferbuilder, tesselator, poseStack, -far, -far, far,  // bottom-left
                -far, far, far,   // top-left
                far, far, far,    // top-right
                far, -far, far,   // bottom-right
                alpha);
        poseStack.popPose();

        // Back face (-Z)
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, backTexture);
        drawQuad(bufferbuilder, tesselator, poseStack, far, -far, -far,
                far, far, -far,
                -far, far, -far,
                -far, -far, -far,
                alpha);
        poseStack.popPose();

        // Left face (-X)
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, leftTexture);
        drawQuad(bufferbuilder, tesselator, poseStack, -far, -far, -far,
                -far, far, -far,
                -far, far, far,
                -far, -far, far,
                alpha);
        poseStack.popPose();

        // Right face (+X)
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, rightTexture);
        drawQuad(bufferbuilder, tesselator, poseStack, far, -far, far,
                far, far, far,
                far, far, -far,
                far, -far, -far,
                alpha);
        poseStack.popPose();

        // Top face (+Y)
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, topTexture);
        drawQuad(bufferbuilder, tesselator, poseStack, -far, far, far,
                -far, far, -far,
                far, far, -far,
                far, far, far,
                alpha);
        poseStack.popPose();

        // Bottom face (-Y)
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, bottomTexture);
        drawQuad(bufferbuilder, tesselator, poseStack, -far, -far, -far,
                -far, -far, far,
                far, -far, far,
                far, -far, -far,
                alpha);
        poseStack.popPose();

        poseStack.popPose();

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    private static void drawQuad(BufferBuilder bufferbuilder, Tesselator tesselator, PoseStack poseStack,
                                 float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float x3, float y3, float z3,
                                 float x4, float y4, float z4,
                                 int alpha) {
        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(matrix4f, x1, y1, z1).uv(0.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
        bufferbuilder.vertex(matrix4f, x2, y2, z2).uv(0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
        bufferbuilder.vertex(matrix4f, x3, y3, z3).uv(1.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
        bufferbuilder.vertex(matrix4f, x4, y4, z4).uv(1.0F, 1.0F).color(255, 255, 255, alpha).endVertex();
        tesselator.end();
    }

    public static void renderEndSky(PoseStack poseStack) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        for(int i = 0; i < 6; ++i) {
            poseStack.pushPose();
            if (i == 1) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            float far = Minecraft.getInstance().gameRenderer.getRenderDistance();
            bufferbuilder.vertex(matrix4f, -far, -far, -far).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, -far, -far, far).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, far, -far, far).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, far, -far, -far).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
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
