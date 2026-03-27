package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.complexpasses.EndBlockRenderPass;
import com.noodlegamer76.shadered.client.renderer.complexpasses.EndSkySkyblockRenderPass;
import com.noodlegamer76.shadered.client.renderer.complexpasses.SkyblockRenderPass;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

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

    public static SkyblockBatchData spaceData = new SkyblockBatchData();
    public static SkyblockBatchData stormyData = new SkyblockBatchData();
    public static SkyblockBatchData oceanData = new SkyblockBatchData();
    public static SkyblockBatchData endData = new SkyblockBatchData();
    public static SkyblockBatchData endSkyData = new SkyblockBatchData();
    public static SkyblockBatchData eclipseData = new SkyblockBatchData();

    public static void setup() {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        Vector3f skyboxRotationSpeed = new Vector3f(0.007f, 0.01f, 0.004f);
        SkyblockRenderPass spaceRenderPass = new SkyblockRenderPass(NEBULA, spaceData,
                new SkyboxTranslation(), skyboxRotationSpeed, GameRenderer::getPositionTexColorShader
        );

        SkyblockRenderPass stormyRenderPass = new SkyblockRenderPass(STORMY, stormyData,
                new SkyboxTranslation()
        );

        SkyblockRenderPass oceanRenderPass = new SkyblockRenderPass(OCEAN, oceanData,
                new SkyboxTranslation()
                        .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                        .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
        );

        SkyblockRenderPass eclipseRenderPass = new SkyblockRenderPass(ECLIPSE, eclipseData,
                new SkyboxTranslation(), skyboxRotationSpeed, () -> RegisterShaders.skyboxWarp
        );

        EndSkySkyblockRenderPass endSkyRenderPass = new EndSkySkyblockRenderPass(endData);
        EndBlockRenderPass endBlockRenderPass = new EndBlockRenderPass(endSkyData);

        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, spaceRenderPass);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, stormyRenderPass);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, oceanRenderPass);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, eclipseRenderPass);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, endSkyRenderPass);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, endBlockRenderPass);

    }

    public static void preRender() {
        int noise = getTextureId(GRAINY2);
        RegisterShaders.skyboxWarp.setSampler("Noise", noise);
    }

    public static int getTextureId(ResourceLocation resourceLocation) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(resourceLocation);
        return abstracttexture.getId();
    }
}