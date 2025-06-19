package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class RenderCube {

    public static void renderSkyBlocks(ArrayList<SkyBlockRenderInfo> info, ShaderInstance shader) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        Minecraft.getInstance().gameRenderer.getMainCamera();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(() -> shader);

        PoseStack poseStack = new PoseStack();

        for (int i = 0; i < info.size(); i++) {
            BlockPos pos = info.get(i).getPos();
            for (int j = 0; j < 6; j++) {
                if (shouldCull(pos, j)) {
                    continue;
                }

                poseStack.pushPose();
                poseStack.mulPose(info.get(i).getPose());

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

                bufferBuilder.addVertex(matrix4f, -1, 0, -1);
                bufferBuilder.addVertex(matrix4f, 1, 0, -1);
                bufferBuilder.addVertex(matrix4f, 1, 0, 1);
                bufferBuilder.addVertex(matrix4f, -1, 0, 1);

                poseStack.popPose();
            }
        }

        MeshData data = bufferBuilder.build();

        if (data != null) {
            BufferUploader.drawWithShader(data);
        }

        info.clear();
    }

    public static void renderCubeWithRenderType(ArrayList<SkyBlockRenderInfo> info, RenderType renderType) {
        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        PoseStack poseStack = new PoseStack();
        for (int i = 0; i < info.size(); i++) {
            BlockPos pos = info.get(i).pos;
            for (int j = 0; j < 6; j++) {
                if (shouldCull(pos, j)) {
                    continue;
                }

                poseStack.pushPose();

                poseStack.mulPose(info.get(i).pose);
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

                vertexConsumer.addVertex(matrix4f, -1, 0, -1);
                vertexConsumer.addVertex(matrix4f, 1, 0, -1);
                vertexConsumer.addVertex(matrix4f, 1, 0, 1);
                vertexConsumer.addVertex(matrix4f, -1, 0, 1);

                poseStack.popPose();
            }
        }

        info.clear();
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
