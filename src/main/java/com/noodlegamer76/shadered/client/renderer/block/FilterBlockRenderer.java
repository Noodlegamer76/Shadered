package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.FilterBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class FilterBlockRenderer implements BlockEntityRenderer<FilterBlockEntity> {
    public FilterBlockRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(FilterBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer.filterData.add(pBlockEntity.getPass(), pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
    }
}
