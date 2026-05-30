package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.WindowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Matrix4f;

public class WindowRenderer implements BlockEntityRenderer<WindowEntity> {

    public WindowRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WindowEntity blockEntity, float partialTick, PoseStack pPoseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        SkyblockRenderer.glassData.add(blockEntity.getPass(), blockEntity.getBlockPos(), new Matrix4f(pPoseStack.last().pose()), false, 1.0F);
    }

    @Override
    public boolean shouldRenderOffScreen(WindowEntity pBlockEntity) {
        return true;
    }
}
