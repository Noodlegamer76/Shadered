package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.entity.block.OceanBlockEntity;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public class OceanBlockRenderer<T extends RenderTester> implements BlockEntityRenderer<OceanBlockEntity> {

    public OceanBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(OceanBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderEventsForFbos.oceanPositions.add(pBlockEntity.getBlockPos());
    }
}