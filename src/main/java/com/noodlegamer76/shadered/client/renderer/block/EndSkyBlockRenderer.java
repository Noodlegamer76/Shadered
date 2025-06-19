package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.RenderTargets;
import com.noodlegamer76.shadered.client.util.SkyBlockRenderInfo;
import com.noodlegamer76.shadered.tile.EndSkyBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class EndSkyBlockRenderer implements BlockEntityRenderer<EndSkyBlockEntity> {

    public EndSkyBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EndSkyBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderTargets.endSkyRenderInfos.add(new SkyBlockRenderInfo(pBlockEntity.getBlockPos(), pPoseStack.last().pose()));
    }

    @Override
    public boolean shouldRender(EndSkyBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
