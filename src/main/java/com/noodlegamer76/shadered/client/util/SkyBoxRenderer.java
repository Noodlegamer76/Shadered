package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import static com.noodlegamer76.shadered.client.util.SkyboxTranslation.*;

import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_SKY_LOCATION;

public class SkyBoxRenderer {
    public static void renderBlockSkybox(PoseStack poseStack, SkyboxTranslation translation,
                                         ShaderInstance shaderInstance,
                                         int r, int g, int b, int a,
                                         ResourceLocation frontTexture, ResourceLocation backTexture, ResourceLocation leftTexture,
                                         ResourceLocation rightTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(() -> shaderInstance);

        float far = Minecraft.getInstance().gameRenderer.getRenderDistance();

        poseStack.pushPose();

        // Front face (−Z, north)
        RenderSystem.setShaderTexture(0, frontTexture);
        drawQuad(poseStack,
                -far, -far, -far,
                far,  -far, -far,
                far,   far, -far,
                -far,  far, -far,
                r, g, b, a,
                translation.frontRot,
                translation.frontFlip
        );

        // Back face (+Z, south)
        RenderSystem.setShaderTexture(0, backTexture);
        drawQuad(poseStack,
                far,  -far,  far,
                -far, -far,  far,
                -far,  far,  far,
                far,   far,  far,
                r, g, b, a,
                translation.backRot,
                translation.backFlip
        );

        // Left face (+X, east)
        RenderSystem.setShaderTexture(0, leftTexture);
        drawQuad(poseStack,
                far,  -far, -far,
                far,  -far,  far,
                far,   far,  far,
                far,   far, -far,
                r, g, b, a,
                translation.leftRot,
                translation.leftFlip
        );

        // Right face (−X, west)
        RenderSystem.setShaderTexture(0, rightTexture);
        drawQuad(poseStack,
                -far, -far,  far,
                -far, -far, -far,
                -far,  far, -far,
                -far,  far,  far,
                r, g, b, a,
                translation.rightRot,
                translation.rightFlip
        );

        // Top face (+Y)
        RenderSystem.setShaderTexture(0, topTexture);
        drawQuad(poseStack,
                -far,  far, -far,
                far,   far, -far,
                far,   far,  far,
                -far,  far,  far,
                r, g, b, a,
                translation.topRot,
                translation.topFlip
        );

        // Bottom face (−Y)
        RenderSystem.setShaderTexture(0, bottomTexture);
        drawQuad(poseStack,
                -far, -far,  far,
                far,  -far,  far,
                far,  -far, -far,
                -far, -far, -far,
                r, g, b, a,
                translation.bottomRot,
                translation.bottomFlip
        );

        poseStack.popPose();

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    public static VertexConsumer startQuad() {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        return bufferBuilder;
    }

    public static void endQuad() {
        Tesselator.getInstance().end();
    }

    private static void vertexUV(VertexConsumer vc, Matrix4f m,
                                 float x, float y, float z,
                                 float u, float v,
                                 int r, int g, int b, int a,
                                 SkyboxRotation rotation,
                                 SkyboxFlip flip) {

        float uu = u;
        float vv = v;

        switch (rotation) {
            case ROTATE_90_CW -> { float t = uu; uu = 1.0F - vv; vv = t; }
            case ROTATE_180   -> { uu = 1.0F - uu; vv = 1.0F - vv; }
            case ROTATE_90_CCW-> { float t = uu; uu = vv; vv = 1.0F - t; }
            default -> {}
        }

        switch (flip) {
            case HORIZONTAL -> uu = 1.0F - uu;
            case VERTICAL   -> vv = 1.0F - vv;
            case BOTH       -> { uu = 1.0F - uu; vv = 1.0F - vv; }
            default -> {}
        }

        vc.vertex(m, x, y, z)
                .uv(uu, vv)
                .color(r, g, b, a)
                .endVertex();
    }



    private static void drawQuad(PoseStack poseStack,
                                 float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float x3, float y3, float z3,
                                 float x4, float y4, float z4,
                                 int r, int g, int b, int a,
                                 SkyboxRotation rotation, SkyboxFlip flip) {

        VertexConsumer vc = startQuad();
        Matrix4f m = poseStack.last().pose();

        vertexUV(vc, m, x1, y1, z1, 0.0F, 1.0F, r, g, b, a, rotation, flip);
        vertexUV(vc, m, x2, y2, z2, 0.0F, 0.0F, r, g, b, a, rotation, flip);
        vertexUV(vc, m, x3, y3, z3, 1.0F, 0.0F, r, g, b, a, rotation, flip);
        vertexUV(vc, m, x4, y4, z4, 1.0F, 1.0F, r, g, b, a, rotation, flip);

        endQuad();
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

    public static void renderBlockSkybox(
            PoseStack poseStack,
            ResourceLocation folder,
            ShaderInstance shaderInstance,
            int r, int g, int b, int a,
            SkyboxTranslation translation) {
        renderBlockSkybox(poseStack,
                translation,
                shaderInstance,
                r, g, b, a,
                folder.withSuffix("/front.png"),
                folder.withSuffix("/back.png"),
                folder.withSuffix("/left.png"),
                folder.withSuffix("/right.png"),
                folder.withSuffix("/top.png"),
                folder.withSuffix("/bottom.png")
        );
    }

    public static void renderBlockSkybox(PoseStack poseStack, ResourceLocation folder, ShaderInstance shaderInstance, SkyboxTranslation translation) {
        renderBlockSkybox(poseStack,
                translation,
                shaderInstance,
                255, 255, 255, 255,
                folder.withSuffix("/front.png"),
                folder.withSuffix("/back.png"),
                folder.withSuffix("/left.png"),
                folder.withSuffix("/right.png"),
                folder.withSuffix("/top.png"),
                folder.withSuffix("/bottom.png")
        );
    }
}
