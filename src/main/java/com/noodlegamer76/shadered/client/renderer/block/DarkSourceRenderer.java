package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.lighting.PointLight;
import com.noodlegamer76.shadered.entity.block.DarkSourceEntity;
import com.noodlegamer76.shadered.entity.block.EndBlockEntity;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import com.noodlegamer76.shadered.event.RenderEventsForLights;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class DarkSourceRenderer implements BlockEntityRenderer<DarkSourceEntity> {

    public DarkSourceRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DarkSourceEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
       // RenderEventsForLights.lights.add(
       //         new PointLight()
       //                 .setConstant(3)
       //                 .setLinear(0)
       //                 .setQuadratic(0.0025f)
       //                 .setAlpha(0.9f)
       //                 .setSubtract()
       //                 .setColor(new Vector3f(0, 0, 0))
       //                 .setPosition(pBlockEntity.getBlockPos().getCenter().toVector3f()));

    }

    @Override
    public boolean shouldRender(DarkSourceEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(DarkSourceEntity pBlockEntity) {
        return true;
    }
}
