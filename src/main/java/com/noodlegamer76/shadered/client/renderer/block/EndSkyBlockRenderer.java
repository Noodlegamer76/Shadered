package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.entity.block.EndSkyBlockEntity;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class EndSkyBlockRenderer implements BlockEntityRenderer<EndSkyBlockEntity> {

    public EndSkyBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EndSkyBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderEventsForFbos.endSkyPositions.add(pBlockEntity.getBlockPos());
        RenderEventsForFbos.endSkyPose.add(pPoseStack.last().pose());
    }

    @Override
    public boolean shouldRender(EndSkyBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
