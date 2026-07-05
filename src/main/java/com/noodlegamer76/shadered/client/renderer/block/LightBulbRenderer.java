package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.shader.lights.Light;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightBulbRenderer implements BlockEntityRenderer<LightBulbEntity> {

    public LightBulbRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(LightBulbEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        LightUploader.addLight(new Light(
                pBlockEntity.getBlockPos().getX() + 0.5f,
                pBlockEntity.getBlockPos().getY() + 0.5f,
                pBlockEntity.getBlockPos().getZ() + 0.5f,
                pBlockEntity.getRed(),
                pBlockEntity.getGreen(),
                pBlockEntity.getBlue(),
                pBlockEntity.getRadius()
        ));
    }

    @Override
    public boolean shouldRender(LightBulbEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(LightBulbEntity pBlockEntity) {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(LightBulbEntity blockEntity) {
        return BlockEntityRenderer.super.getRenderBoundingBox(blockEntity).inflate(blockEntity.getRadius());
    }
}
