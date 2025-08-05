package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.GlUtils;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.shadered.client.util.SkyBoxRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;


@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {
    public static final ResourceLocation NEBULA = new ResourceLocation(ShaderedMod.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(ShaderedMod.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(ShaderedMod.MODID, "textures/environment/ocean");
    private static boolean fboSetup = false;

    public static TextureTarget nebulaTarget;
    public static TextureTarget stormyTarget;
    public static TextureTarget oceanTarget;
    public static TextureTarget endSkyTarget;

    public static TextureTarget skyboxTarget;

    public static int width;
    public static int height;

    public static int previousSizeX;
    public static int previousSizeY;

    public static ArrayList<BlockPos> spacePositions = new ArrayList<>();
    public static ArrayList<BlockPos> stormyPositions = new ArrayList<>();
    public static ArrayList<BlockPos> oceanPositions = new ArrayList<>();
    public static ArrayList<BlockPos> endPositions = new ArrayList<>();
    public static ArrayList<BlockPos> endSkyPositions = new ArrayList<>();

    public static ArrayList<Matrix4f> spacePose = new ArrayList<>();
    public static ArrayList<Matrix4f> stormyPose = new ArrayList<>();
    public static ArrayList<Matrix4f> oceanPose = new ArrayList<>();
    public static ArrayList<Matrix4f> endPose = new ArrayList<>();
    public static ArrayList<Matrix4f> endSkyPose = new ArrayList<>();

    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY && !fboSetup) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            nebulaTarget = new TextureTarget(width, height, true, true);
            stormyTarget = new TextureTarget(width, height, true, true);
            oceanTarget = new TextureTarget(width, height, true, true);
            endSkyTarget = new TextureTarget(width, height, true, true);
            skyboxTarget = new TextureTarget(width, height, false, true);

            previousSizeX = width;
            previousSizeY = height;
            fboSetup = true;
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            nebulaTarget.clear(true);
            stormyTarget.clear(true);
            oceanTarget.clear(true);
            endSkyTarget.clear(true);
            skyboxTarget.clear(true);

            if (width != previousSizeX || height != previousSizeY) {
                nebulaTarget.resize(width, height, true);
                stormyTarget.resize(width, height, true);
                oceanTarget.resize(width, height, true);
                endSkyTarget.resize(width, height, true);
                skyboxTarget.resize(width, height, true);
            }

            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            Minecraft mc = Minecraft.getInstance();
            RenderTarget mainTarget = mc.getMainRenderTarget();

            nebulaTarget.bindWrite(true);
            renderBlockEntities(event.getPartialTick(), spacePositions, spacePose);

            skyboxTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), NEBULA);
            GlUtils.copyColorFrom(nebulaTarget, skyboxTarget);

            nebulaTarget.bindWrite(true);
            renderWithoutInfiniteDepth(nebulaTarget, event.getPoseStack());



            stormyTarget.bindWrite(true);
            renderBlockEntities(event.getPartialTick(), stormyPositions, stormyPose);

            skyboxTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), STORMY);
            GlUtils.copyColorFrom(stormyTarget, skyboxTarget);

            stormyTarget.bindWrite(true);
            renderWithoutInfiniteDepth(stormyTarget, event.getPoseStack());



            oceanTarget.bindWrite(true);
            renderBlockEntities(event.getPartialTick(), oceanPositions, oceanPose);

            skyboxTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), OCEAN);
            GlUtils.copyColorFrom(oceanTarget, skyboxTarget);

            oceanTarget.bindWrite(true);
            renderWithoutInfiniteDepth(oceanTarget, event.getPoseStack());



            endSkyTarget.bindWrite(true);
            renderBlockEntities(event.getPartialTick(), endSkyPositions, endSkyPose);

            skyboxTarget.bindWrite(true);
            SkyBoxRenderer.renderEndSky(event.getPoseStack());
            GlUtils.copyColorFrom(endSkyTarget, skyboxTarget);

            endSkyTarget.bindWrite(true);
            renderWithoutInfiniteDepth(endSkyTarget, event.getPoseStack());



            mainTarget.bindWrite(true);



            RenderSystem.backupProjectionMatrix();
            RenderSystem.setProjectionMatrix(new Matrix4f().ortho(-1, 1, -1, 1, -1, 1), VertexSorting.ORTHOGRAPHIC_Z);

            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.setIdentity();

            render(poseStack, nebulaTarget);
            render(poseStack, stormyTarget);
            render(poseStack, oceanTarget);
            render(poseStack, endSkyTarget);

            RenderCube.renderCubeWithRenderType(endPositions, event.getPartialTick(), RenderType.endPortal(), endPose);

            poseStack.popPose();

            RenderSystem.restoreProjectionMatrix();
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;
        }
    }

    public static void renderWithoutInfiniteDepth(TextureTarget target, PoseStack poseStack) {
        RenderSystem.setShader(() -> RegisterShadersEvent.depthDiscarder);
        RegisterShadersEvent.depthDiscarder.setSampler("Skybox", target.getColorTextureId());
        RegisterShadersEvent.depthDiscarder.setSampler("SkyboxDepth", target.getDepthTextureId());
        RegisterShadersEvent.depthDiscarder.setSampler("MainDepth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
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
        buffer.vertex(poseStack.last().pose(), -1f,  1f, 0f).uv(0, 1).endVertex(); // top-left
        buffer.vertex(poseStack.last().pose(), 1f,  1f, 0f).uv(1, 1).endVertex(); // top-right
        buffer.vertex(poseStack.last().pose(), 1f, -1f, 0f).uv(1, 0).endVertex(); // bottom-right
        buffer.vertex(poseStack.last().pose(), -1f, -1f, 0f).uv(0, 0).endVertex(); // bottom-left
        tesselator.end();

        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
    }


    public static void renderBlockEntities(float partialTick, ArrayList<BlockPos> positions, ArrayList<Matrix4f> poses) {
        if (positions.size() != poses.size()) {
            return;
        }

        RenderCube.renderSkyBlocks(positions, partialTick, poses, GameRenderer.getPositionColorShader());
    }
}
