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
    private static final PoseStack REUSABLE_POSESTACK = new PoseStack();

    public static void renderSkyBlocks(ArrayList<SkyBlockRenderInfo> info, ShaderInstance shader) {
        if (info.isEmpty()) {
            return;
        }

        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,
                DefaultVertexFormat.POSITION);
        Minecraft.getInstance().gameRenderer.getMainCamera();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(() -> shader);

        for (int i = 0; i < info.size(); i++) {
            BlockPos pos = info.get(i).getPos();
            for (int j = 0; j < 6; j++) {
                if (shouldCull(pos, j)) {
                    continue;
                }

                REUSABLE_POSESTACK.pushPose();
                REUSABLE_POSESTACK.last().pose().set(info.get(i).getPose());
                REUSABLE_POSESTACK.translate(0.5, 0.5, 0.5);

                switch (j) {
                    case 0:
                        break;
                    case 1:
                        REUSABLE_POSESTACK.mulPose(Axis.XP.rotationDegrees(90));
                        break;
                    case 2:
                        REUSABLE_POSESTACK.mulPose(Axis.XP.rotationDegrees(180));
                        break;
                    case 3:
                        REUSABLE_POSESTACK.mulPose(Axis.XP.rotationDegrees(-90));
                        break;
                    case 4:
                        REUSABLE_POSESTACK.mulPose(Axis.ZP.rotationDegrees(-90));
                        break;
                    case 5:
                        REUSABLE_POSESTACK.mulPose(Axis.ZN.rotationDegrees(-90));
                        break;
                }

                REUSABLE_POSESTACK.translate(0, -0.5, 0);
                REUSABLE_POSESTACK.scale(0.5f, 0.5f, 0.5f);

                Matrix4f matrix4f = REUSABLE_POSESTACK.last().pose();

                bufferBuilder.addVertex(matrix4f, -1, 0, -1);
                bufferBuilder.addVertex(matrix4f, 1, 0, -1);
                bufferBuilder.addVertex(matrix4f, 1, 0, 1);
                bufferBuilder.addVertex(matrix4f, -1, 0, 1);

                REUSABLE_POSESTACK.popPose();
            }
        }

        MeshData data = bufferBuilder.build();

        if (data != null) {
            BufferUploader.drawWithShader(data);
        }
    }

    public static void renderCubeWithRenderType(ArrayList<SkyBlockRenderInfo> info, RenderType renderType) {
        if (info.isEmpty()) {
            return;
        }

        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        for (int i = 0; i < info.size(); i++) {
            BlockPos pos = info.get(i).pos;
            for (int j = 0; j < 6; j++) {
                if (shouldCull(pos, j)) {
                    continue;
                }

                REUSABLE_POSESTACK.pushPose();
                REUSABLE_POSESTACK.last().pose().set(info.get(i).pose);
                REUSABLE_POSESTACK.translate(0.5, 0.5, 0.5);

                switch (j) {
                    case 0:
                        break;
                    case 1:
                        REUSABLE_POSESTACK.mulPose(Axis.XP.rotationDegrees(90));
                        break;
                    case 2:
                        REUSABLE_POSESTACK.mulPose(Axis.XP.rotationDegrees(180));
                        break;
                    case 3:
                        REUSABLE_POSESTACK.mulPose(Axis.XP.rotationDegrees(-90));
                        break;
                    case 4:
                        REUSABLE_POSESTACK.mulPose(Axis.ZP.rotationDegrees(-90));
                        break;
                    case 5:
                        REUSABLE_POSESTACK.mulPose(Axis.ZN.rotationDegrees(-90));
                        break;
                }

                REUSABLE_POSESTACK.translate(0, -0.5, 0);
                REUSABLE_POSESTACK.scale(0.5f, 0.5f, 0.5f);

                Matrix4f matrix4f = REUSABLE_POSESTACK.last().pose();

                vertexConsumer.addVertex(matrix4f, -1, 0, -1);
                vertexConsumer.addVertex(matrix4f, 1, 0, -1);
                vertexConsumer.addVertex(matrix4f, 1, 0, 1);
                vertexConsumer.addVertex(matrix4f, -1, 0, 1);

                REUSABLE_POSESTACK.popPose();
            }
        }
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
