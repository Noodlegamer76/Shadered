package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.OceanBlockEntity;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class OceanBlockRenderer<T extends RenderTester> implements BlockEntityRenderer<OceanBlockEntity> {

    public OceanBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(OceanBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer.oceanData.add(pBlockEntity.getPass(), pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
    }

    @Override
    public boolean shouldRender(OceanBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
