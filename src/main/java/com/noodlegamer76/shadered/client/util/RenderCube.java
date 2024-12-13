package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class RenderCube {

    public static void renderSkyBlocks(PoseStack poseStack, ArrayList<BlockPos> positions, float partialTicks) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(() -> RegisterShadersEvent.skybox);

        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (BlockPos pos : positions) {
            for (int i = 0; i < 6; i++) {
                // Check for backface culling based on face direction
                if (shouldCull(pos, i)) {
                    continue; // Skip rendering backfaces
                }

                poseStack.pushPose();
                poseStack.translate(0.5, 0.5, 0.5);

                poseStack.translate(-camera.x, -camera.y, -camera.z);
                poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

                switch (i) {
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

                bufferBuilder.vertex(matrix4f, -1, 0, -1).endVertex();
                bufferBuilder.vertex(matrix4f, 1, 0, -1).endVertex();
                bufferBuilder.vertex(matrix4f, 1, 0, 1).endVertex();
                bufferBuilder.vertex(matrix4f, -1, 0, 1).endVertex();

                poseStack.popPose();
            }
        }

        tesselator.end();

        positions.clear();
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
