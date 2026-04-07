package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.LightBlockEntity;
import com.noodlegamer76.shadered.entity.block.MimicBlockEntity;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class MimicBlockRenderer<T extends RenderTester> implements BlockEntityRenderer<MimicBlockEntity> {

    public MimicBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MimicBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer.mimicData.add(pBlockEntity.getPass(), pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
    }

    @Override
    public boolean shouldRender(MimicBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
