package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class SkyblockRenderer {
    public static final ResourceLocation NEBULA = new ResourceLocation(ShaderedMod.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(ShaderedMod.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(ShaderedMod.MODID, "textures/environment/ocean");
    public static final ResourceLocation ECLIPSE = new ResourceLocation(ShaderedMod.MODID, "textures/environment/eclipse");

    public static final ResourceLocation GRAINY = new ResourceLocation(ShaderedMod.MODID, "textures/noise/grainy.png");
    public static final ResourceLocation GRAINY2 = new ResourceLocation(ShaderedMod.MODID, "textures/noise/grainy2.png");
    public static final ResourceLocation MANIFOLD = new ResourceLocation(ShaderedMod.MODID, "textures/noise/manifold.png");
    public static final ResourceLocation MILKY = new ResourceLocation(ShaderedMod.MODID, "textures/noise/milky.png");
    public static final ResourceLocation SWIRL = new ResourceLocation(ShaderedMod.MODID, "textures/noise/swirl.png");
    private static boolean fboSetup = false;

    public static TextureTarget nebulaTarget;
    public static TextureTarget stormyTarget;
    public static TextureTarget oceanTarget;
    public static TextureTarget endSkyTarget;
    public static TextureTarget eclipseTarget;

    public static TextureTarget skyboxTarget;

    public static int width;
    public static int height;

    public static int previousSizeX;
    public static int previousSizeY;

    public static SkyblockBatchData spaceData = new SkyblockBatchData();
    public static SkyblockBatchData stormyData = new SkyblockBatchData();
    public static SkyblockBatchData oceanData = new SkyblockBatchData();
    public static SkyblockBatchData endData = new SkyblockBatchData();
    public static SkyblockBatchData endSkyData = new SkyblockBatchData();
    public static SkyblockBatchData eclipseData = new SkyblockBatchData();

    public static void renderSkyblocks(RenderLevelStageEvent.Stage stage, PoseStack poseStack, int renderTick, float partialTick) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_SKY && !fboSetup) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            nebulaTarget = new TextureTarget(width, height, true, true);
            stormyTarget = new TextureTarget(width, height, true, true);
            oceanTarget = new TextureTarget(width, height, true, true);
            endSkyTarget = new TextureTarget(width, height, true, true);
            eclipseTarget = new TextureTarget(width, height, true, true);
            skyboxTarget = new TextureTarget(width, height, false, true);

            previousSizeX = width;
            previousSizeY = height;
            fboSetup = true;
        }

        if (stage == RenderLevelStageEvent.Stage.AFTER_SKY) {
            RegisterShaders.invert.setSampler("Color", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
            RegisterShaders.compressor.setSampler("Color", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
            RegisterShaders.compressor.setSampler("Manifold", getTextureId(new ResourceLocation(ShaderedMod.MODID, "textures/noise/manifold.png")));
            RegisterShaders.compressor.setSampler("Grainy2", getTextureId(new ResourceLocation(ShaderedMod.MODID, "textures/noise/grainy2.png")));

            Uniform screenSize = RegisterShaders.compressor.getUniform("ScreenSize");
            if (screenSize != null) {
                screenSize.set((float) width, (float) height);
            }

            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            nebulaTarget.clear(true);
            stormyTarget.clear(true);
            oceanTarget.clear(true);
            endSkyTarget.clear(true);
            eclipseTarget.clear(true);
            skyboxTarget.clear(true);

            if (width != previousSizeX || height != previousSizeY) {
                nebulaTarget.resize(width, height, true);
                stormyTarget.resize(width, height, true);
                oceanTarget.resize(width, height, true);
                endSkyTarget.resize(width, height, true);
                eclipseTarget.resize(width, height, true);
                skyboxTarget.resize(width, height, true);

                previousSizeX = width;
                previousSizeY = height;
            }

            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        }

        if (stage == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            Minecraft mc = Minecraft.getInstance();
            RenderTarget mainTarget = mc.getMainRenderTarget();



            Quaternionf spaceRotation = new Quaternionf();
            float ticks = (renderTick + partialTick);
            spaceRotation.mul(Axis.YN.rotationDegrees(ticks * 0.01f));
            spaceRotation.mul(Axis.XP.rotationDegrees(ticks * 0.007f));
            spaceRotation.mul(Axis.ZP.rotationDegrees(ticks * 0.004f));

            nebulaTarget.bindWrite(true);
            renderBlockEntities(partialTick, spaceData);

            poseStack.pushPose();
            poseStack.mulPose(spaceRotation);

            skyboxTarget.bindWrite(true);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            SkyBoxRenderer.renderBlockSkybox(poseStack, NEBULA,
                    GameRenderer.getPositionTexColorShader(),
                    new SkyboxTranslation()
            );

            poseStack.popPose();

            GlUtils.copyColorFrom(nebulaTarget, skyboxTarget);

            nebulaTarget.bindWrite(true);
            renderWithoutInfiniteDepth(nebulaTarget, poseStack);


            stormyTarget.bindWrite(true);
            renderBlockEntities(partialTick, stormyData);

            skyboxTarget.bindWrite(true);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            SkyBoxRenderer.renderBlockSkybox(poseStack, STORMY,
                    GameRenderer.getPositionTexColorShader(),
                    new SkyboxTranslation()
            );
            GlUtils.copyColorFrom(stormyTarget, skyboxTarget);

            stormyTarget.bindWrite(true);
            renderWithoutInfiniteDepth(stormyTarget, poseStack);


            oceanTarget.bindWrite(true);
            renderBlockEntities(partialTick, oceanData);

            skyboxTarget.bindWrite(true);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            SkyBoxRenderer.renderBlockSkybox(poseStack, OCEAN,
                    GameRenderer.getPositionTexColorShader(),
                    new SkyboxTranslation()
                            .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                            .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
            );
            GlUtils.copyColorFrom(oceanTarget, skyboxTarget);

            oceanTarget.bindWrite(true);
            renderWithoutInfiniteDepth(oceanTarget, poseStack);



            eclipseTarget.bindWrite(true);
            renderBlockEntities(partialTick, eclipseData);

            poseStack.pushPose();
            poseStack.mulPose(spaceRotation);

            skyboxTarget.bindWrite(true);

            RenderSystem.setShader(() -> RegisterShaders.skyboxWarp);

            int noise = getTextureId(GRAINY2);
            RegisterShaders.skyboxWarp.setSampler("Noise", noise);
            SkyBoxRenderer.renderBlockSkybox(poseStack, ECLIPSE,
                    RegisterShaders.skyboxWarp,
                    new SkyboxTranslation()
            );

            poseStack.popPose();

            GlUtils.copyColorFrom(eclipseTarget, skyboxTarget);

            eclipseTarget.bindWrite(true);
            renderWithoutInfiniteDepth(eclipseTarget, poseStack);



            endSkyTarget.bindWrite(true);
            renderBlockEntities(partialTick, endSkyData);

            skyboxTarget.bindWrite(true);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            SkyBoxRenderer.renderEndSky(poseStack);
            GlUtils.copyColorFrom(endSkyTarget, skyboxTarget);

            endSkyTarget.bindWrite(true);
            renderWithoutInfiniteDepth(endSkyTarget, poseStack);


            mainTarget.bindWrite(true);


            RenderSystem.backupProjectionMatrix();
            RenderSystem.setProjectionMatrix(new Matrix4f().ortho(-1, 1, -1, 1, -1, 1), VertexSorting.ORTHOGRAPHIC_Z);

            poseStack.pushPose();
            poseStack.setIdentity();

            render(poseStack, nebulaTarget);
            render(poseStack, stormyTarget);
            render(poseStack, oceanTarget);
            render(poseStack, eclipseTarget);
            render(poseStack, endSkyTarget);

            poseStack.popPose();

            RenderSystem.restoreProjectionMatrix();

            RenderCube.renderCubeWithRenderType(RenderType.endPortal(), endData);
        }

        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;
        }
    }

    public static void renderWithoutInfiniteDepth(TextureTarget target, PoseStack poseStack) {
        RenderSystem.setShader(() -> RegisterShaders.depthDiscarder);
        RegisterShaders.depthDiscarder.setSampler("Skybox", target.getColorTextureId());
        RegisterShaders.depthDiscarder.setSampler("SkyboxDepth", target.getDepthTextureId());
        RegisterShaders.depthDiscarder.setSampler("MainDepth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        RenderCubeAroundPlayer.renderCubeWithShader(poseStack);

        Minecraft.getInstance().getMainRenderTarget().copyDepthFrom(target);

    }

    public static void render(PoseStack poseStack, RenderTarget target) {
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, target.getColorTextureId());

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(poseStack.last().pose(), -1f, 1f, 0f).uv(0, 1).endVertex(); // top-left
        buffer.vertex(poseStack.last().pose(), 1f, 1f, 0f).uv(1, 1).endVertex(); // top-right
        buffer.vertex(poseStack.last().pose(), 1f, -1f, 0f).uv(1, 0).endVertex(); // bottom-right
        buffer.vertex(poseStack.last().pose(), -1f, -1f, 0f).uv(0, 0).endVertex(); // bottom-left
        tesselator.end();

        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
    }


    public static void renderBlockEntities(float partialTick, SkyblockBatchData data) {
        if (data.getPositions().size() != data.getPose().size()) {
            data.clear();
            return;
        }

        RenderCube.renderSkyBlocks(data, GameRenderer.getPositionShader());
    }

    public static int getTextureId(ResourceLocation resourceLocation) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(resourceLocation);
        return abstracttexture.getId();
    }
}