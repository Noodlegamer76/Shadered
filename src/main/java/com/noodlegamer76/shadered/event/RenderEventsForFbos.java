package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.ShaderedMod;
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
import org.lwjgl.opengl.GL44;

import java.util.ArrayList;

import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_SKY_LOCATION;


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
    public static int spaceTexture;
    public static int stormyTexture;
    public static int oceanTexture;
    public static int endSkyTexture;
    public static int width;
    public static int height;

    private static int previousSizeX;
    private static int previousSizeY;

    public static ArrayList<BlockPos> spacePositions = new ArrayList<>();
    public static ArrayList<BlockPos> stormyPositions = new ArrayList<>();
    public static ArrayList<BlockPos> oceanPositions = new ArrayList<>();
    public static ArrayList<BlockPos> endPositions = new ArrayList<>();
    public static ArrayList<BlockPos> endSkyPositions = new ArrayList<>();
    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY && !fboSetup) {
            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
            previousSizeX = width;
            previousSizeY = height;
            fboSetup = true;

            spaceFbo = GlStateManager.glGenFramebuffers();
            stormyFbo = GlStateManager.glGenFramebuffers();
            oceanFbo = GlStateManager.glGenFramebuffers();
            endSkyFbo = GlStateManager.glGenFramebuffers();
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

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();
            changeTextureSize();

            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, spaceFbo);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), NEBULA);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, stormyFbo);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), STORMY);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, oceanFbo);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), OCEAN);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, endSkyFbo);
            SkyBoxRenderer.renderEndSky(event.getPoseStack());

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {


            //BATCH RENDER SKY BLOCKS HERE
            RegisterShadersEvent.skybox.setSampler("Skybox", spaceTexture);
            RenderCube.renderSkyBlocks(event.getPoseStack(), spacePositions, event.getPartialTick());

            RegisterShadersEvent.skybox.setSampler("Skybox", stormyTexture);
            RenderCube.renderSkyBlocks(event.getPoseStack(), stormyPositions, event.getPartialTick());

            RegisterShadersEvent.skybox.setSampler("Skybox", oceanTexture);
            RenderCube.renderSkyBlocks(event.getPoseStack(), oceanPositions, event.getPartialTick());

            RegisterShadersEvent.skybox.setSampler("Skybox", endSkyTexture);
            RenderCube.renderSkyBlocks(event.getPoseStack(), endSkyPositions, event.getPartialTick());

            RenderCube.renderCubeWithRenderType(event.getPoseStack(), endPositions, event.getPartialTick(), RenderType.endPortal());

        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;
        }

    }


    public static void changeTextureSize() {
        if (previousSizeX != width || previousSizeY != height) {
            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);

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

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
        }
    }
}
