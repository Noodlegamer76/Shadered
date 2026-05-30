package com.noodlegamer76.shadered.client.renderer.block.skyblock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.entity.block.skyblock.StormyBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class StormyBlockRenderer<T extends RenderTester>  implements BlockEntityRenderer<StormyBlockEntity> {

    public StormyBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StormyBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer.stormyData.add(pBlockEntity.getPass(), pBlockEntity.getBlockPos(), new Matrix4f(pPoseStack.last().pose()), false, 1.0F);
    }

    @Override
    public boolean shouldRender(StormyBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
