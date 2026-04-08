package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.EndBlockEntity;
import com.noodlegamer76.shadered.entity.block.WindowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class WindowRenderer implements BlockEntityRenderer<WindowEntity> {

    public WindowRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WindowEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        SkyblockRenderer.glassData.add(blockEntity.getPass(), blockEntity.getBlockPos(), poseStack.last().pose(), false, 1.0F);
    }

    @Override
    public boolean shouldRenderOffScreen(WindowEntity pBlockEntity) {
        return true;
    }
}
