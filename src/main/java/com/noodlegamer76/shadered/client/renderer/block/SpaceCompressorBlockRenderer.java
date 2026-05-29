package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.entity.block.SpaceCompressorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

    @Override
    public AABB getRenderBoundingBox(SpaceCompressorBlockEntity blockEntity) {
        BlockPos pos1 = blockEntity.getPos1();
        BlockPos pos2 = blockEntity.getPos2();
        if (pos1 == null || pos2 == null) {
            return BlockEntityRenderer.super.getRenderBoundingBox(blockEntity);
        } else {
            Vec3i min = new Vec3i(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
            Vec3i max = new Vec3i(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
            return new AABB(new Vec3(min.getX(), min.getY(), min.getZ()), new Vec3(max.getX() + 1, max.getY() + 1, max.getZ() + 1));
        }
    }
}
