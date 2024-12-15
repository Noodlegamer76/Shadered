package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.entity.block.StormyBlockEntity;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

public class StormyBlockRenderer<T extends RenderTester>  implements BlockEntityRenderer<StormyBlockEntity> {

    public StormyBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StormyBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        RenderEventsForFbos.stormyPositions.add(pBlockEntity.getBlockPos());
    }

    @Override
    public boolean shouldRender(StormyBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
