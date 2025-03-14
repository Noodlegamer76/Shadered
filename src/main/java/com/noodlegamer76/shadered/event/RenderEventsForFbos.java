package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.SkyBoxRenderer;
import com.noodlegamer76.shadered.compat.iris.ShaderInjector;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.gl.IrisRenderSystem;
import net.irisshaders.iris.gl.framebuffer.GlFramebuffer;
import net.irisshaders.iris.gl.program.ProgramSamplers;
import net.irisshaders.iris.gl.sampler.GlSampler;
import net.irisshaders.iris.gl.texture.*;
import net.irisshaders.iris.gl.uniform.UniformHolder;
import net.irisshaders.iris.helpers.Tri;
import net.irisshaders.iris.pipeline.CustomTextureManager;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.samplers.IrisImages;
import net.irisshaders.iris.samplers.IrisSamplers;
import net.irisshaders.iris.shaderpack.ShaderPack;
import net.irisshaders.iris.shaderpack.loading.ProgramId;
import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import net.irisshaders.iris.shaderpack.texture.CustomTextureData;
import net.irisshaders.iris.shaderpack.texture.TextureFilteringData;
import net.irisshaders.iris.shaderpack.texture.TextureStage;
import net.irisshaders.iris.texture.TextureTracker;
import net.irisshaders.iris.uniforms.custom.CustomUniformFixedInputUniformsHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL44;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.mojang.blaze3d.platform.GlConst.GL_COLOR_BUFFER_BIT;
import static com.noodlegamer76.shadered.compat.iris.ShaderInjector.getTextureData;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;


@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {
    public static final ResourceLocation NEBULA = new ResourceLocation(ShaderedMod.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(ShaderedMod.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(ShaderedMod.MODID, "textures/environment/ocean");
    private static final Logger log = LoggerFactory.getLogger(RenderEventsForFbos.class);
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

    public static ShaderPack pack = null;

    public static GlFramebuffer spaceGlFbo;

    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY && !fboSetup) {
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
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();
            changeTextureSize();


            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, spaceFbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT, true);
            if (IrisApi.getInstance().isShaderPackInUse() && spaceGlFbo != null) {
                spaceGlFbo.bind();
            }

            //if (!spacePositions.isEmpty()) {
                SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), NEBULA);
            //}

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, stormyFbo);
            GlStateManager._clear(GL_COLOR_BUFFER_BIT, true);
            //if (!stormyPositions.isEmpty()) {
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), STORMY);
            //}

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

            if (IrisApi.getInstance().isShaderPackInUse() && pack != null) {
                Window window = Minecraft.getInstance().getWindow();
                //pack.getIrisCustomTextureDataMap().put("skybox1212", new CustomTextureData.RawData2D(getTextureData(RenderEventsForFbos.stormyTexture, window.getWidth(), window.getHeight()),
                //        new TextureFilteringData(false, false), InternalTextureFormat.RGBA, PixelFormat.RGBA, PixelType.BYTE, window.getWidth(), window.getHeight()));
                ProgramSource source = pack.getProgramSet(Iris.getCurrentDimension()).getGbuffersBlock().get();
                //Iris.getPipelineManager().getPipeline().get().getTextureMap().put(new Tri<>("skybox1212", TextureType.TEXTURE_2D, TextureStage.GBUFFERS_AND_SHADOW), "skybox1212");

                //pack.getIrisCustomTextureDataMap().put("skybox1212", new CustomTextureData.RawData2D(ShaderInjector.getTextureData(spaceTexture, window.getWidth(), window.getHeight()),
                //        new TextureFilteringData(false, false), InternalTextureFormat.RGBA, PixelFormat.RGBA, PixelType.UNSIGNED_BYTE, window.getWidth(), window.getHeight()));


                //pack.getIrisCustomTextureDataMap().put("skybox1212", new CustomTextureData.RawData2D(ShaderInjector.getTextureData(spaceTexture, window.getWidth(), window.getHeight()),
                //        new TextureFilteringData(false, true), InternalTextureFormat.RGBA, PixelFormat.RGBA, PixelType.UNSIGNED_BYTE, window.getWidth(), window.getHeight()));


               // IrisSamplers.addCustomTextures(,
               //         Object2ObjectMaps.singleton("skybox1212",
               //                 new GlTexture(TextureType.TEXTURE_2D,
               //                         window.getWidth(), window.getHeight(), 0,
               //                         InternalTextureFormat.RGBA.getGlFormat(),
               //                         PixelFormat.RGBA.getGlFormat(),
               //                         PixelType.UNSIGNED_BYTE.getGlFormat(),
               //                         getTextureData(spaceTexture, window.getWidth(), window.getHeight()),
               //                         new TextureFilteringData(false, true))));
                //if (
                //        ProgramSamplers.builder(ProgramId.Block.ordinal(), Set.of(31)).addDynamicSampler(
                //                TextureType.TEXTURE_2D, () -> spaceFbo, new GlSampler(true, true, false, false), "skybox1212"
                //        )) {
                //    System.out.println(true);
                //}
            }
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {



            //BATCH RENDER SKY BLOCKS HERE
            //RenderCube.renderSkyBlocks(spacePositions, event.getPartialTick(), spacePose, RegisterShadersEvent.spaceShader);

           //RenderCube.renderSkyBlocks(stormyPositions, event.getPartialTick(), stormyPose, RegisterShadersEvent.stormyShader);

           //RenderCube.renderSkyBlocks(oceanPositions, event.getPartialTick(), oceanPose, RegisterShadersEvent.oceanShader);

           //RenderCube.renderSkyBlocks(endSkyPositions, event.getPartialTick(), endSkyPose, RegisterShadersEvent.endSkyShader);

            RenderCube.renderCubeWithRenderType(endPositions, event.getPartialTick(), RenderType.endPortal(), endPose);

        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;

        }
    }


    public static void changeTextureSize() {
        if (previousSizeX != width || previousSizeY != height) {
            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);

            RenderEventsForMaps.deleteTexturesAndFbos();
            RenderEventsForMaps.createTexturesAndFbos();






            //SPACE

            GlStateManager._glDeleteFramebuffers(spaceFbo);
            spaceFbo = IrisRenderSystem.createFramebuffer();

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, spaceFbo);

            GlStateManager._deleteTexture(spaceTexture);

            spaceTexture = IrisRenderSystem.createTexture(GL_TEXTURE_2D);
            IrisRenderSystem.bindTextureForSetup(GL_TEXTURE_2D, spaceTexture);

            IrisRenderSystem.texImage2D(spaceTexture, GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            IrisRenderSystem.framebufferTexture2D(spaceFbo, GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, spaceTexture, 0);






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
        }
    }
}
