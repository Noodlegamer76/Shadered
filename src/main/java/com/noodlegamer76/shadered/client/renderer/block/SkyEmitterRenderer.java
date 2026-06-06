package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.renderer.assimp.RenderableModel;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class SkyEmitterRenderer implements BlockEntityRenderer<SkyEmitterEntity> {
    private RenderableModel renderableModel;

    public SkyEmitterRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SkyEmitterEntity skyEmitter, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        SkyblockType type = skyEmitter.getBlockType();
        SkyblockPass pass = skyEmitter.getPass();
        if (type == null || pass == null) return;

        BlockPos pos = skyEmitter.getBlockPos();
        float maxAlpha = skyEmitter.getAlpha();
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        float distance = (float) cameraPos.distanceTo(Vec3.atCenterOf(pos));
        float minRange = skyEmitter.getMinimumRange();
        float maxRange = skyEmitter.getMaximumRange();
        float renderTime = mc.levelRenderer.getTicks() + partialTick;

        float t = Mth.clamp((distance - minRange) / (maxRange - minRange), 0.0f, 1.0f);
        float alpha = Mth.lerp(t, maxAlpha, 0.0f);


        pos = pos.atY(4000 + pos.getY());

        renderSkyblockSkybox(pos, pass, poseStack, type.getData(), alpha, renderTime);
    }

    @Override
    public int getViewDistance() {
        return 4096;
    }

    private void renderSkyblockSkybox(BlockPos pos, SkyblockPass pass, PoseStack poseStack, SkyblockBatchData data, float alpha, float renderTime) {
        PoseStack test = new PoseStack();

        test.translate(-5, -5, -5);
        test.scale(10, 10, 10);

        data.add(SkyblockPass.BACKGROUND, pos, new Matrix4f(test.last().pose()), true, alpha);

        poseStack.pushPose();

        poseStack.translate(0.5, 1, 0.5);

        poseStack.scale(0.5f, 0.5f, 0.5f);
        Quaternionf rotation = new Quaternionf();
        rotation.mul(Axis.YP.rotationDegrees(renderTime * 1.0f));
        rotation.mul(Axis.ZP.rotationDegrees(renderTime * 2.0f));
        rotation.mul(Axis.XN.rotationDegrees(renderTime * 3.0f));
        poseStack.mulPose(rotation);

        poseStack.translate(-0.5, -0.5, -0.5);

        data.add(SkyblockPass.NORMAL, pos, new Matrix4f(poseStack.last().pose()), false, alpha);

        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(SkyEmitterEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(SkyEmitterEntity pBlockEntity) {
        return true;
    }
}
