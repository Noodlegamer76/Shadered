package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.puddle.PuddleCubeRenderer;
import com.noodlegamer76.shadered.client.renderer.puddle.PuddleInfo;
import com.noodlegamer76.shadered.client.util.ExtendedShaderInstance;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.SkyBoxRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL44;

import java.util.ArrayList;

import static com.mojang.blaze3d.platform.GlConst.GL_COLOR_BUFFER_BIT;


@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {
    public static final ResourceLocation NEBULA = new ResourceLocation(ShaderedMod.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(ShaderedMod.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(ShaderedMod.MODID, "textures/environment/ocean");
    private static boolean fboSetup = false;
    public static int spaceFbo;
    public static int stormyFbo;
    public static int oceanFbo;
    public static int endSkyFbo;
    public static int painting1Fbo;
    public static int spaceTexture;
    public static int stormyTexture;
    public static int oceanTexture;
    public static int endSkyTexture;
    public static int painting1ColorTexture;
    public static int painting1DepthTexture;
    public static RenderTarget albedoTarget;
    public static RenderTarget tempTarget;
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

            RenderEventsForMaps.createTexturesAndFbos();

            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
            previousSizeX = width;
            previousSizeY = height;
            fboSetup = true;

            spaceFbo = GlStateManager.glGenFramebuffers();
            stormyFbo = GlStateManager.glGenFramebuffers();
            oceanFbo = GlStateManager.glGenFramebuffers();
            endSkyFbo = GlStateManager.glGenFramebuffers();
            painting1Fbo = GlStateManager.glGenFramebuffers();
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, spaceFbo);

            //SPACE TEXTURE
            spaceTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(spaceTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, spaceTexture, 0);



            //STORMY TEXTURE
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, stormyFbo);

            stormyTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(stormyTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, stormyTexture, 0);

            //OCEAN TEXTURE
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, oceanFbo);

            oceanTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(oceanTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, oceanTexture, 0);

            //END SKY TEXTURE
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, endSkyFbo);

            endSkyTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(endSkyTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, endSkyTexture, 0);

            //PAINTING 1 TEXTURE
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, painting1Fbo);

            painting1ColorTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(painting1ColorTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, painting1ColorTexture, 0);

            //PAINTING 1 DEPTH TEXTURE
            painting1DepthTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(painting1DepthTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_DEPTH_COMPONENT24,
                    width, height,
                    0, GL44.GL_DEPTH_COMPONENT, GL44.GL_FLOAT, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);

            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_DEPTH_ATTACHMENT, GL44.GL_TEXTURE_2D, painting1DepthTexture, 0);


            //BACK TO CURRENT FBO
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);

            albedoTarget = new TextureTarget(width, height, false, false);
            tempTarget = new TextureTarget(width, height, false, false);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();
            changeTextureSize();

            ExtendedShaderInstance.setUniforms();
            ExtendedShaderInstance.setSamplers();


            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, spaceFbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT, true);
            //if (!spacePositions.isEmpty()) {
                SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), NEBULA);
            //}

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, stormyFbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT, true);
            //if (!stormyPositions.isEmpty()) {
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), STORMY);
            //Minecraft mc = Minecraft.getInstance();
            //Vec3 vec3 = event.getCamera().getPosition();
            //double d0 = vec3.x();
            //double d1 = vec3.y();
            //double d2 = vec3.z();
            //float f = mc.gameRenderer.getRenderDistance();
            //boolean flag1 = mc.level.effects().isFoggyAt(Mth.floor(d0), Mth.floor(d1)) || mc.gui.getBossOverlay().shouldCreateWorldFog();
            //FogRenderer.setupFog(event.getCamera(), FogRenderer.FogMode.FOG_SKY, f, flag1, event.getPartialTick());
            //RenderSystem.setShader(GameRenderer::getPositionShader);
            //Minecraft.getInstance().levelRenderer.renderSky(event.getPoseStack(), event.getProjectionMatrix(), event.getPartialTick(), event.getCamera(), flag1, () -> {
            //    FogRenderer.setupFog(event.getCamera(), FogRenderer.FogMode.FOG_SKY, f, flag1, event.getPartialTick());
            //});
            //RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
            //mc.gameRenderer.lightTexture().turnOffLightLayer();
            //mc.levelRenderer.renderClouds(event.getPoseStack(), event.getProjectionMatrix(), event.getPartialTick(), d0, d1, d2);
            ////}

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, oceanFbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT, true);
            //if (!oceanPositions.isEmpty()) {
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), OCEAN);
            //}

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, endSkyFbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT, true);
            //if (!endSkyPositions.isEmpty()) {
            SkyBoxRenderer.renderEndSky(event.getPoseStack());
            //}

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, painting1Fbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT | GL44.GL_DEPTH_BUFFER_BIT, true);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
            
            RegisterShadersEvent.spaceShader.setSampler("Skybox", spaceTexture);
            RegisterShadersEvent.stormyShader.setSampler("Skybox", stormyTexture);
            RegisterShadersEvent.oceanShader.setSampler("Skybox", oceanTexture);
            RegisterShadersEvent.endSkyShader.setSampler("Skybox", endSkyTexture);
            RegisterShadersEvent.painting1Shader.setSampler("Skybox", painting1ColorTexture);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {



            //BATCH RENDER SKY BLOCKS HERE
            RenderCube.renderSkyBlocks(spacePositions, event.getPartialTick(), spacePose, RegisterShadersEvent.spaceShader);

            RenderCube.renderSkyBlocks(stormyPositions, event.getPartialTick(), stormyPose, RegisterShadersEvent.stormyShader);

            RenderCube.renderSkyBlocks(oceanPositions, event.getPartialTick(), oceanPose, RegisterShadersEvent.oceanShader);

            RenderCube.renderSkyBlocks(endSkyPositions, event.getPartialTick(), endSkyPose, RegisterShadersEvent.endSkyShader);

            RenderCube.renderCubeWithRenderType(endPositions, event.getPartialTick(), RenderType.endPortal(), endPose);

        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;

        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            ExtendedShaderInstance.setUniforms();

            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();

            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();

            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            for (PuddleInfo info: PuddleCubeRenderer.puddles) {
                PuddleCubeRenderer.renderPuddles(event.getPoseStack(), info);
            }
            if (!PuddleCubeRenderer.puddles.isEmpty()) {
                //note to future self: memory barrier comes after tesselator ends
                GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
            }
            tesselator.end();
            RenderSystem.enableDepthTest();
            if (!PuddleCubeRenderer.puddles.isEmpty()) {
                //note to future self: memory barrier comes after tesselator ends
                GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
            }


        }
    }


    public static void changeTextureSize() {
        if (previousSizeX != width || previousSizeY != height) {
            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);

            RenderEventsForMaps.deleteTexturesAndFbos();
            RenderEventsForMaps.createTexturesAndFbos();

            //SPACE
            GlStateManager._glDeleteFramebuffers(spaceFbo);
            spaceFbo = GlStateManager.glGenFramebuffers();

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, spaceFbo);

            GlStateManager._deleteTexture(spaceTexture);

            spaceTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(spaceTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, spaceTexture, 0);

            //STORMY
            GlStateManager._glDeleteFramebuffers(stormyFbo);
            stormyFbo = GlStateManager.glGenFramebuffers();

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, stormyFbo);

            GlStateManager._deleteTexture(stormyTexture);

            stormyTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(stormyTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, stormyTexture, 0);

            //OCEAN
            GlStateManager._glDeleteFramebuffers(oceanFbo);
            oceanFbo = GlStateManager.glGenFramebuffers();

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, oceanFbo);

            GlStateManager._deleteTexture(oceanTexture);

            oceanTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(oceanTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, oceanTexture, 0);

            //END SKY
            GlStateManager._glDeleteFramebuffers(endSkyFbo);
            endSkyFbo = GlStateManager.glGenFramebuffers();

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, endSkyFbo);

            GlStateManager._deleteTexture(endSkyTexture);

            endSkyTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(endSkyTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, endSkyTexture, 0);

            //PAINTING 1
            GlStateManager._glDeleteFramebuffers(painting1Fbo);
            painting1Fbo = GlStateManager.glGenFramebuffers();

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, painting1Fbo);

            GlStateManager._deleteTexture(painting1ColorTexture);

            painting1ColorTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(painting1ColorTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, painting1ColorTexture, 0);


            //PAINTING 1 DEPTH TEXTURE
            GlStateManager._deleteTexture(painting1DepthTexture);

            painting1DepthTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(painting1DepthTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_DEPTH_COMPONENT24,
                    width, height,
                    0, GL44.GL_DEPTH_COMPONENT, GL44.GL_FLOAT, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);

            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_DEPTH_ATTACHMENT, GL44.GL_TEXTURE_2D, painting1DepthTexture, 0);

            //BACK TO CURRENT
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);

            albedoTarget = new TextureTarget(width, height, false, false);
            tempTarget = new TextureTarget(width, height, false, false);
        }
    }
}
