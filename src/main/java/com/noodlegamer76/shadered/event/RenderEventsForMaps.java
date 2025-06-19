package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.shadered.client.util.lighting.Light;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL44;

import javax.sound.midi.MidiChannel;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForMaps {
    public static int fabulousDepthTexture = -1;
    public static int mapFbo = -1;
    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {
        if (mapFbo == -1) {
            return;
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            if (fabulousDepthTexture != -1 && Minecraft.getInstance().options.graphicsMode().get() == GraphicsStatus.FABULOUS) {
                renderFabulousDepth(event.getPoseStack());
            }
        }

    }

    private static void renderFabulousDepth(PoseStack poseStack) {
        //tried making a texture for fabulous graphics, doesn't work for some reason
        //if you figure out what's wrong talk to me on discord @noodlegamer76
        //check the post_depth shader
        int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, mapFbo);

        ShaderInstance postDepth = RegisterShadersEvent.postDepth;
        LevelRenderer renderer = Minecraft.getInstance().levelRenderer;

        RenderSystem.clearDepth(1.0);

        RenderSystem.setShader(() -> postDepth);
        GlStateManager._glUseProgram(postDepth.getId());

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        postDepth.setSampler("EntityDepth", renderer.entityTarget().getDepthTextureId());
        postDepth.setSampler("CloudDepth", renderer.getCloudsTarget().getDepthTextureId());
        postDepth.setSampler("ItemEntityDepth", renderer.getItemEntityTarget().getDepthTextureId());
        postDepth.setSampler("TranslucentDepth", renderer.getTranslucentTarget().getDepthTextureId());
        postDepth.setSampler("WeatherDepth", renderer.getWeatherTarget().getDepthTextureId());
        postDepth.setSampler("ParticlesDepth", renderer.getParticlesTarget().getDepthTextureId());

        RenderCubeAroundPlayer.renderCubeWithShader(poseStack);
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
    }

    public static void createTexturesAndFbos() {
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();

        int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
        mapFbo = GlStateManager.glGenFramebuffers();

        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, mapFbo);

        fabulousDepthTexture = GlStateManager._genTexture();
        RenderSystem.bindTexture(fabulousDepthTexture);

        GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_DEPTH_COMPONENT32,
                width, height,
                0, GL44.GL_DEPTH_COMPONENT, GL44.GL_FLOAT, null);

        GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
        GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);
        GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_WRAP_S, GL44.GL_CLAMP_TO_EDGE);
        GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_WRAP_T, GL44.GL_CLAMP_TO_EDGE);

        GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_DEPTH_ATTACHMENT, GL44.GL_TEXTURE_2D, fabulousDepthTexture, 0);

        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
    }

    public static void deleteTexturesAndFbos() {
        GlStateManager._glDeleteFramebuffers(mapFbo);
        GlStateManager._deleteTexture(fabulousDepthTexture);

        mapFbo = -1;
        fabulousDepthTexture = -1;
    }
}
