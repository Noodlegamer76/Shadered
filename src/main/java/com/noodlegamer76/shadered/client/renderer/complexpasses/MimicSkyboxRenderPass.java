package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

public class MimicSkyboxRenderPass implements RenderableComplexPass {
    private final SkyblockBatchData batchData;
    private TextureTarget skyboxTarget;

    public MimicSkyboxRenderPass(SkyblockBatchData batchData) {
        this.batchData = batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {

        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        if (skyboxTarget == null) {
            skyboxTarget = new TextureTarget(renderer.getPreviousWidth(), renderer.getPreviousHeight(), true, Minecraft.ON_OSX);
            SkyblockRenderer.DATA_LIST.put(batchData, skyboxTarget.getColorTextureId());
        }
        else if (skyboxTarget.width != renderer.getPreviousWidth() || skyboxTarget.height != renderer.getPreviousHeight()) {
            skyboxTarget.resize(renderer.getPreviousWidth(), renderer.getPreviousHeight(), Minecraft.ON_OSX);
            SkyblockRenderer.DATA_LIST.put(batchData, skyboxTarget.getColorTextureId());
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        poseStack = new PoseStack();

        skyboxTarget.bindWrite(true);
        poseStack.pushPose();

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        float renderDistance = mc.gameRenderer.getRenderDistance();
        LevelRenderer levelRenderer = mc.levelRenderer;
        GameRenderer gameRenderer = mc.gameRenderer;

        FogShape oldFogShape = RenderSystem.getShaderFogShape();
        float oldFogEnd = RenderSystem.getShaderFogEnd();
        float oldFogStart = RenderSystem.getShaderFogStart();
        float[] oldFogColor = RenderSystem.getShaderFogColor();

        // Setup fog color for clearing
        FogRenderer.setupColor(
                camera,
                partialTick,
                mc.level,
                mc.options.getEffectiveRenderDistance(),
                gameRenderer.getDarkenWorldAmount(partialTick)
        );

        float[] fogColor = RenderSystem.getShaderFogColor();

        RenderSystem.clearColor(fogColor[0], fogColor[1], fogColor[2], 1.0F);
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);

        boolean isFoggy = mc.level.effects().isFoggyAt(
                Mth.floor(cameraPos.x),
                Mth.floor(cameraPos.y)
        ) || mc.gui.getBossOverlay().shouldCreateWorldFog();

        FogRenderer.levelFogColor();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableBlend();

        Quaternionf quaternionf = camera.rotation().conjugate(new Quaternionf());
        Matrix4f frustum = new Matrix4f().rotation(quaternionf);

        RenderSystem.setShader(GameRenderer::getPositionShader);
        levelRenderer.renderSky(
                frustum,
                RenderSystem.getProjectionMatrix(),
                partialTick,
                camera,
                isFoggy,
                () -> FogRenderer.setupFog(
                        camera,
                        FogRenderer.FogMode.FOG_SKY,
                        renderDistance,
                        isFoggy,
                        partialTick
                )
        );

        FogRenderer.setupFog(
                camera,
                FogRenderer.FogMode.FOG_TERRAIN,
                renderDistance,
                isFoggy,
                partialTick
        );

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShader(GameRenderer::getRendertypeCloudsShader);
        levelRenderer.renderClouds(
                poseStack,
                frustum,
                RenderSystem.getProjectionMatrix(),
                partialTick,
                cameraPos.x,
                cameraPos.y,
                cameraPos.z
        );

        poseStack.popPose();

        RenderSystem.disableBlend();
        RenderSystem.colorMask(false, false, false, true);
        RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 1.0F);
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT, Minecraft.ON_OSX);
        RenderSystem.colorMask(true, true, true, true);

        renderer.getRenderBuffer().bindWrite(true);


        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderFogShape(oldFogShape);
        RenderSystem.setShaderFogEnd(oldFogEnd);
        RenderSystem.setShaderFogStart(oldFogStart);
        RenderSystem.setShaderFogColor(
                oldFogColor[0],
                oldFogColor[1],
                oldFogColor[2]
        );
    }

    public TextureTarget getSkyboxTarget() {
        return skyboxTarget;
    }
}
