package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.client.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class SkyblockRenderer {
    private static final SkyblockRenderer instance = new SkyblockRenderer();

    public static SkyblockRenderer getInstance() {
        return instance;
    }

    public static final ResourceLocation NEBULA = new ResourceLocation(Shadered.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(Shadered.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(Shadered.MODID, "textures/environment/ocean");
    public static final ResourceLocation ECLIPSE = new ResourceLocation(Shadered.MODID, "textures/environment/eclipse");
    private boolean fboSetup = false;

    private TextureTarget nebulaTarget;
    private TextureTarget stormyTarget;
    private TextureTarget oceanTarget;
    private TextureTarget endSkyTarget;
    private TextureTarget eclipseTarget;

    public SkyblockData nebulaData = new SkyblockData();
    public SkyblockData stormyData = new SkyblockData();
    public SkyblockData oceanData = new SkyblockData();
    public SkyblockData endSkyData = new SkyblockData();
    public SkyblockData eclipseData = new SkyblockData();
    public SkyblockData endData = new SkyblockData();

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

            PoseStack poseStack = event.getPoseStack();


            nebulaTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, NEBULA,
                    new SkyboxTranslation()
            );

            stormyTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, STORMY,
                    new SkyboxTranslation()
            );

            oceanTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, OCEAN,
                    new SkyboxTranslation()
                            .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                            .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
            );

            eclipseTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(poseStack, ECLIPSE,
                    new SkyboxTranslation()
            );

            endSkyTarget.bindWrite(true);
            SkyBoxRenderer.renderEndSky(poseStack);

            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            RenderCube.renderSkyBlocks(nebulaData, ModRenderTypes.SPACE);
            RenderCube.renderSkyBlocks(stormyData, ModRenderTypes.STORMY);
            RenderCube.renderSkyBlocks(oceanData, ModRenderTypes.OCEAN);
            RenderCube.renderSkyBlocks(endSkyData, ModRenderTypes.END_SKY);
            RenderCube.renderSkyBlocks(eclipseData, ModRenderTypes.ECLIPSE);

            RenderCube.renderSkyBlocks(endData, RenderType.endGateway());
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
