package com.noodlegamer76.shadered.client.util;

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
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
            bufferbuilder.vertex(matrix4f, -far, -far, -far).endVertex();
            bufferbuilder.vertex(matrix4f, -far, -far, far).endVertex();
            bufferbuilder.vertex(matrix4f, far, -far, far).endVertex();
            bufferbuilder.vertex(matrix4f, far, -far, -far).endVertex();
            tesselator.end();
            poseStack.popPose();
        }
    }

    public static void renderCubeWithShader(PoseStack poseStack, Color color) {

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
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.vertex(matrix4f, -1, -1, -1).color(color.getRGB()).endVertex();
            bufferbuilder.vertex(matrix4f, -1, -1, 1).color(color.getRGB()).endVertex();
            bufferbuilder.vertex(matrix4f, 1, -1, 1).color(color.getRGB()).endVertex();
            bufferbuilder.vertex(matrix4f, 1, -1, -1).color(color.getRGB()).endVertex();
            poseStack.popPose();
        }
    }
}
