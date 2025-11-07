package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.RenderTargets;
import com.noodlegamer76.shadered.client.util.SkyBlockRenderInfo;
import com.noodlegamer76.shadered.tile.EndBlockEntity;
import com.noodlegamer76.shadered.tile.Ps1BlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class Ps1BlockRenderer implements BlockEntityRenderer<Ps1BlockEntity> {

    public Ps1BlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(Ps1BlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderTargets.ps1Infos.add(new SkyBlockRenderInfo(pBlockEntity.getBlockPos(), pPoseStack.last().pose()));
    }

    @Override
    public boolean shouldRender(Ps1BlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
