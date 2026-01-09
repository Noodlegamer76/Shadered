package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.EclipseBlockEntity;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class EclipseBlockEntityRenderer implements BlockEntityRenderer<EclipseBlockEntity> {

    public EclipseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EclipseBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer renderer = SkyblockRenderer.getInstance();
        renderer.eclipseData.add(pBlockEntity.getBlockPos(), pPoseStack.last().pose());
    }

    @Override
    public boolean shouldRender(EclipseBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
