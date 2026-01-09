package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.entity.block.SpaceBlockEntity;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class SpaceBlockRenderer<T extends RenderTester> implements BlockEntityRenderer<SpaceBlockEntity> {

    public SpaceBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpaceBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer renderer = SkyblockRenderer.getInstance();
        renderer.nebulaData.add(pBlockEntity.getBlockPos(), pPoseStack.last().pose());
    }

    @Override
    public boolean shouldRender(SpaceBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
