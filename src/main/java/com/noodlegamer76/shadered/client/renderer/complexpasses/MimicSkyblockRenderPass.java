package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.skyblock.SkyBoxRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.client.util.skyblock.SkyboxTranslation;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.util.function.Supplier;

public class MimicSkyblockRenderPass implements RenderableComplexPass {
    private final SkyblockBatchData batchData;

    public MimicSkyblockRenderPass(SkyblockBatchData batchData) {
        this.batchData = batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        if (batchData.isEmpty()) return;
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();
        TextureTarget skyboxTarget = renderer.getWriteBuffer();

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) return;

        poseStack = new PoseStack();

        skyboxTarget.bindWrite(true);
        poseStack.pushPose();

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        float f = mc.gameRenderer.getRenderDistance();
        LevelRenderer levelRenderer = mc.levelRenderer;
        GameRenderer gameRenderer = mc.gameRenderer;

        poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        poseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));

        FogRenderer.setupColor(camera, partialTick, mc.level, mc.options.getEffectiveRenderDistance(), gameRenderer.getDarkenWorldAmount(partialTick));

        float[] fogColor = RenderSystem.getShaderFogColor();
        RenderSystem.clearColor(fogColor[0], fogColor[1], fogColor[2], 1.0F);
        RenderSystem.clear(16640, Minecraft.ON_OSX);

        boolean flag1 = mc.level.effects().isFoggyAt(Mth.floor(cameraPos.x), Mth.floor(cameraPos.y)) ||
                mc.gui.getBossOverlay().shouldCreateWorldFog();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, f, flag1, partialTick);

        RenderSystem.setShader(GameRenderer::getPositionShader);
        levelRenderer.renderSky(poseStack, RenderSystem.getProjectionMatrix(), partialTick, camera, flag1,
                () -> FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, f, flag1, partialTick));

        RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
        levelRenderer.renderClouds(poseStack, RenderSystem.getProjectionMatrix(), partialTick, cameraPos.x, cameraPos.y, cameraPos.z);

        poseStack.popPose();
        renderer.getRenderBuffer().bindWrite(true);

        for (SkyblockPass pass : SkyblockPass.values()) {
            ShaderInstance shader = pass.getShader();
            shader.setSampler("Skybox", skyboxTarget.getColorTextureId());

            RenderCube.renderSkyBlocks(batchData.get(pass), false, shader);
        }

        batchData.clear();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
