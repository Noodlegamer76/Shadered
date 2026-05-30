package com.noodlegamer76.shadered.client.renderer.block.skyblock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.skyblock.EndSkyBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class EndSkyBlockRenderer implements BlockEntityRenderer<EndSkyBlockEntity> {

    public EndSkyBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EndSkyBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer.endSkyData.add(pBlockEntity.getPass(), pBlockEntity.getBlockPos(), new Matrix4f(pPoseStack.last().pose()), false, 1.0F);
    }

    @Override
    public boolean shouldRender(EndSkyBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
