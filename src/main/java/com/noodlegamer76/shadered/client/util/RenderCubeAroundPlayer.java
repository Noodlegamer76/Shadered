package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;

import java.awt.*;

public class RenderCubeAroundPlayer {

    public static void renderCubeWithShader(PoseStack poseStack) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        for(int i = 0; i < 6; ++i) {
            poseStack.pushPose();
            if (i == 0) {

                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 1) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(-90));
            }

            if (i == 2) {

                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
            }

            if (i == 3) {

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
            }

            if (i == 4) {

                poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }

            if (i == 5) {

                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
            }
            float far = Minecraft.getInstance().gameRenderer.getRenderDistance();
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f, -far, -far, -far).uv(0, 0).endVertex();
            bufferbuilder.vertex(matrix4f, -far, -far,  far).uv(0, 1).endVertex();
            bufferbuilder.vertex(matrix4f,  far, -far,  far).uv(1, 1).endVertex();
            bufferbuilder.vertex(matrix4f,  far, -far, -far).uv(1, 0).endVertex();
            tesselator.end();
            poseStack.popPose();
        }
    }

    public static void renderCubeWithShader(PoseStack poseStack, Color color) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();

        poseStack.pushPose();

        poseStack.scale(100.0f, 100.0f, 100.0f);

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < 6; ++i) {
            poseStack.pushPose();

            switch (i) {
                case 0: // top
                    break;
                case 1:
                    poseStack.mulPose(Axis.XP.rotationDegrees(90));
                    break;
                case 2:
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    break;
                case 3:
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                    break;
                case 4:
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                    break;
                case 5:
                    poseStack.mulPose(Axis.ZN.rotationDegrees(-90));
                    break;
            }

            Matrix4f matrix4f = poseStack.last().pose();

            bufferbuilder.vertex(matrix4f, -1, 0, -1).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
            bufferbuilder.vertex(matrix4f,  1, 0, -1).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
            bufferbuilder.vertex(matrix4f,  1, 0,  1).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
            bufferbuilder.vertex(matrix4f, -1, 0,  1).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();

            poseStack.popPose();
        }

        tesselator.end();

        poseStack.popPose();

        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
