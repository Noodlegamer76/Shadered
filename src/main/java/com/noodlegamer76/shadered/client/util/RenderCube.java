package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderCube {

    public static void renderSkyBlocks(SkyblockData data, RenderType renderType) {
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        RenderSystem.setShader(() -> RegisterShadersEvent.skybox);

        PoseStack poseStack = new PoseStack();

        for (int i = 0; i < data.getPos().size(); i++) {
            BlockPos pos = data.getPos().get(i);
            for (int j = 0; j < 6; j++) {
                if (shouldCull(pos, j)) {
                    continue;
                }

                poseStack.pushPose();
                poseStack.mulPoseMatrix(data.getMatrices().get(i));
                poseStack.translate(0.5, 0.5, 0.5);

                switch (j) {
                    case 0:
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

                poseStack.translate(0, -0.5, 0);
                poseStack.scale(0.5f, 0.5f, 0.5f);

                Matrix4f matrix4f = poseStack.last().pose();

                vertexConsumer.vertex(matrix4f, -1, 0, -1).endVertex();
                vertexConsumer.vertex(matrix4f, 1, 0, -1).endVertex();
                vertexConsumer.vertex(matrix4f, 1, 0, 1).endVertex();
                vertexConsumer.vertex(matrix4f, -1, 0, 1).endVertex();

                poseStack.popPose();
            }
        }

        data.clear();
    }

    public static void renderCubeWithRenderType(List<BlockPos> positions, float partialTicks, RenderType renderType, List<Matrix4f> pose) {
        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        PoseStack poseStack = new PoseStack();

        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i) == null || pose.get(i) == null) continue;

            BlockPos pos = positions.get(i);
            for (int j = 0; j < 6; j++) {
                if (shouldCull(pos, j)) continue;

                poseStack.pushPose();
                poseStack.mulPoseMatrix(pose.get(i));
                poseStack.translate(0.5, 0.5, 0.5);

                switch (j) {
                    case 0:
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

                poseStack.translate(0, -0.5, 0);
                poseStack.scale(0.5f, 0.5f, 0.5f);

                Matrix4f matrix4f = poseStack.last().pose();
                Vector3f normal = switch (j) {
                    case 0 -> new Vector3f(0, -1, 0);
                    case 1 -> new Vector3f(1, 0, 0);
                    case 3 -> new Vector3f(-1, 0, 0);
                    case 4 -> new Vector3f(0, 0, -1);
                    case 5 -> new Vector3f(0, 0, 1);
                    default -> new Vector3f(0, 1, 0);
                };

                // Define vertex data
                float[][] vertices = {
                        {-1, 0, -1},
                        {1, 0, -1},
                        {1, 0, 1},
                        {-1, 0, 1}
                };

                for (float[] v : vertices) {
                    vertexConsumer
                            .vertex(matrix4f, v[0], v[1], v[2])
                            .color(255, 255, 255, 255)
                            .uv(0f, 0f)
                            .uv2(0, 0)
                            .normal(normal.x(), normal.y(), normal.z())
                            .endVertex();
                }

                poseStack.popPose();
            }
        }

        if (positions instanceof ArrayList) {
            positions.clear();
        }
        if (pose instanceof ArrayList) {
            pose.clear();
        }
    }

    public static void renderSpaceCompressorBox(BlockPos[] positions, float partialTicks, RenderType renderType, PoseStack poseStack) {
        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);

        float offset = 0.0005f;

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        BlockPos compressorPos = positions[0];
        BlockPos p1 = positions[1];
        BlockPos p2 = positions[2];

        int minX = Math.min(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int maxX = Math.max(p1.getX(), p2.getX());
        int maxY = Math.max(p1.getY(), p2.getY());
        int maxZ = Math.max(p1.getZ(), p2.getZ());

        float sizeX = (maxX - minX) + 1f;
        float sizeY = (maxY - minY) + 1f;
        float sizeZ = (maxZ - minZ) + 1f;

        poseStack.pushPose();
        poseStack.translate(-compressorPos.getX(), -compressorPos.getY(), -compressorPos.getZ());
        poseStack.translate(minX, minY, minZ);

        poseStack.scale(sizeX, sizeY, sizeZ);

        for (int j = 0; j < 6; j++) {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.5, 0.5);

            switch (j) {
                case 0 -> { /* down */ }
                case 1 -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
                case 2 -> poseStack.mulPose(Axis.XP.rotationDegrees(180));
                case 3 -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                case 4 -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                case 5 -> poseStack.mulPose(Axis.ZN.rotationDegrees(-90));
            }

            poseStack.translate(0, -0.5, 0);
            poseStack.scale(0.5f, 0.5f, 0.5f);

            Matrix4f matrix4f = poseStack.last().pose();

            Vector3f normal = switch (j) {
                case 0 -> new Vector3f(0, 1, 0);
                case 1 -> new Vector3f(-1, 0, 0);
                case 2 -> new Vector3f(0, -1, 0);
                case 3 -> new Vector3f(1, 0, 0);
                case 4 -> new Vector3f(0, 0, 1);
                case 5 -> new Vector3f(0, 0, -1);
                default -> new Vector3f(0, -1, 0);
            };

            float[][] vertices = {
                    { 1f - offset, offset, -1f + offset},
                    {-1f + offset, offset, -1f + offset},
                    {-1f + offset, offset,  1f - offset},
                    { 1f - offset, offset,  1f - offset}
            };

            float[][] uvs = {
                    {1f, 0f},
                    {0f, 0f},
                    {0f, 1f},
                    {1f, 1f}
            };

            for (int k = 0; k < 4; k++) {
                float[] v = vertices[k];
                float[] uv = uvs[k];
                vertexConsumer
                        .vertex(matrix4f, v[0], v[1], v[2])
                        .uv(uv[0], uv[1])
                        .endVertex();
            }

            poseStack.popPose();
        }

        poseStack.popPose();
    }

    private static boolean shouldCull(BlockPos pos, int faceIndex) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return false;
        }

        BlockState neighborState = switch (faceIndex) {
            case 0 -> level.getBlockState(pos.relative(Direction.DOWN));
            case 1 -> level.getBlockState(pos.relative(Direction.NORTH));
            case 2 -> level.getBlockState(pos.relative(Direction.UP));
            case 3 -> level.getBlockState(pos.relative(Direction.SOUTH));
            case 4 -> level.getBlockState(pos.relative(Direction.WEST));
            case 5 -> level.getBlockState(pos.relative(Direction.EAST));
            default -> null;
        };

        return neighborState != null && neighborState.isSolidRender(level, pos);
    }

}
