package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Quaternionf;

public class SkyblockRenderer {
    private static final SkyblockRenderer instance = new SkyblockRenderer();

    public static SkyblockRenderer getInstance() {
        return instance;
    }

    public static final ResourceLocation NEBULA = new ResourceLocation(Shadered.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(Shadered.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(Shadered.MODID, "textures/environment/ocean");
    public static final ResourceLocation ECLIPSE = new ResourceLocation(Shadered.MODID, "textures/environment/eclipse");

    public static final ResourceLocation GRAINY = new ResourceLocation(Shadered.MODID, "textures/noise/grainy.png");
    public static final ResourceLocation GRAINY2 = new ResourceLocation(Shadered.MODID, "textures/noise/grainy2.png");
    public static final ResourceLocation MANIFOLD = new ResourceLocation(Shadered.MODID, "textures/noise/manifold.png");
    public static final ResourceLocation MILKY = new ResourceLocation(Shadered.MODID, "textures/noise/milky.png");
    public static final ResourceLocation SWIRL = new ResourceLocation(Shadered.MODID, "textures/noise/swirl.png");
    private boolean fboSetup = false;

    private TextureTarget nebulaTarget;
    private TextureTarget stormyTarget;
    private TextureTarget oceanTarget;
    private TextureTarget endSkyTarget;
    private TextureTarget eclipseTarget;

    private int width;
    private int height;
    private int previousSizeX;
    private int previousSizeY;

    public void init() {
        width = Minecraft.getInstance().getWindow().getWidth();
        height = Minecraft.getInstance().getWindow().getHeight();

        nebulaTarget = new TextureTarget(width, height, true, true);
        stormyTarget = new TextureTarget(width, height, true, true);
        oceanTarget = new TextureTarget(width, height, true, true);
        endSkyTarget = new TextureTarget(width, height, true, true);
        eclipseTarget = new TextureTarget(width, height, true, true);

        previousSizeX = width;
        previousSizeY = height;
        fboSetup = true;
    }

    public void renderSkybox(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            if (!fboSetup) {
                init();
            }

            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            nebulaTarget.clear(true);
            stormyTarget.clear(true);
            oceanTarget.clear(true);
            endSkyTarget.clear(true);
            eclipseTarget.clear(true);

            if (width != previousSizeX || height != previousSizeY) {
                nebulaTarget.resize(width, height, true);
                stormyTarget.resize(width, height, true);
                oceanTarget.resize(width, height, true);
                endSkyTarget.resize(width, height, true);
                eclipseTarget.resize(width, height, true);
            }

            int renderTick = event.getRenderTick();
            float partialTick = event.getPartialTick();
            float ticks = renderTick + partialTick;
            PoseStack poseStack = event.getPoseStack();

            int noise = getTextureId(GRAINY2);
            RegisterShaders.skyboxWarp.setSampler("Noise", noise);

            Quaternionf spaceRotation = new Quaternionf();
            spaceRotation.mul(Axis.YN.rotationDegrees(ticks * 0.01f));
            spaceRotation.mul(Axis.XP.rotationDegrees(ticks * 0.007f));
            spaceRotation.mul(Axis.ZP.rotationDegrees(ticks * 0.004f));

            poseStack.pushPose();
            poseStack.mulPose(spaceRotation);

            nebulaTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, NEBULA,
                    GameRenderer.getPositionTexColorShader(),
                    new SkyboxTranslation()
            );

            poseStack.popPose();

            stormyTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, STORMY,
                    GameRenderer.getPositionTexColorShader(),
                    new SkyboxTranslation()
            );

            oceanTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, OCEAN,
                    GameRenderer.getPositionTexColorShader(),
                    new SkyboxTranslation()
                            .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                            .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
            );

            poseStack.pushPose();
            poseStack.mulPose(spaceRotation);

            eclipseTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, ECLIPSE,
                    RegisterShaders.skyboxWarp,
                    new SkyboxTranslation()
            );

            poseStack.popPose();

            endSkyTarget.bindWrite(true);
            SkyBoxRenderer.renderEndSky(poseStack);

            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;
        }
    }

    public static int getTextureId(ResourceLocation resourceLocation) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(resourceLocation);
        return abstracttexture.getId();
    }

    public TextureTarget getEclipseTarget() {
        if (!fboSetup) {
            init();
        }
        return eclipseTarget;
    }

    public TextureTarget getNebulaTarget() {
        if (!fboSetup) {
            init();
        }
        return nebulaTarget;
    }

    public TextureTarget getStormyTarget() {
        if (!fboSetup) {
            init();
        }
        return stormyTarget;
    }

    public TextureTarget getOceanTarget() {
        if (!fboSetup) {
            init();
        }
        return oceanTarget;
    }

    public TextureTarget getEndSkyTarget() {
        if (!fboSetup) {
            init();
        }
        return endSkyTarget;
    }
}
