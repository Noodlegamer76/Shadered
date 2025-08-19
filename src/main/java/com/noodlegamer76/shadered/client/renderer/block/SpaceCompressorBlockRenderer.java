package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.entity.block.SpaceCompressorBlockEntity;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;

import java.util.ArrayList;
import java.util.List;

public class SpaceCompressorBlockRenderer implements BlockEntityRenderer<SpaceCompressorBlockEntity> {
    public SpaceCompressorBlockRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(SpaceCompressorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {

        if (blockEntity.getPos1() == null || blockEntity.getPos2() == null) return;

        RenderCube.renderSpaceCompressorBox(
                new BlockPos[] {blockEntity.getBlockPos(), blockEntity.getPos1(), blockEntity.getPos2()},
                partialTick,
                ModRenderTypes.WARP_TRANSPARENT,
                poseStack
        );
    }
}
