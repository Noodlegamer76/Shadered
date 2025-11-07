package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.RenderTargets;
import com.noodlegamer76.shadered.client.util.SkyBlockRenderInfo;
import com.noodlegamer76.shadered.tile.EclipseBlockEntity;
import com.noodlegamer76.shadered.tile.EndBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class EclipseBlockRenderer implements BlockEntityRenderer<EclipseBlockEntity> {

    public EclipseBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EclipseBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderTargets.eclipseInfos.add(new SkyBlockRenderInfo(pBlockEntity.getBlockPos(), pPoseStack.last().pose()));
    }

    @Override
    public boolean shouldRender(EclipseBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
