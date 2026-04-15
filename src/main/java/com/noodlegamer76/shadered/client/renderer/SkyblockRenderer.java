package com.noodlegamer76.shadered.client.renderer;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.complexpasses.*;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyboxTranslation;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class SkyblockRenderer {
    public static final ResourceLocation NEBULA = new ResourceLocation(ShaderedMod.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = new ResourceLocation(ShaderedMod.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = new ResourceLocation(ShaderedMod.MODID, "textures/environment/ocean");
    public static final ResourceLocation ECLIPSE = new ResourceLocation(ShaderedMod.MODID, "textures/environment/eclipse");
    public static final ResourceLocation LIGHT = new ResourceLocation(ShaderedMod.MODID, "textures/environment/light");
    public static final ResourceLocation FOREST = new ResourceLocation(ShaderedMod.MODID, "textures/environment/forest");
    public static final ResourceLocation IRIDIA = new ResourceLocation(ShaderedMod.MODID, "textures/environment/iridia");

    public static final ResourceLocation GRAINY = new ResourceLocation(ShaderedMod.MODID, "textures/noise/grainy.png");
    public static final ResourceLocation GRAINY2 = new ResourceLocation(ShaderedMod.MODID, "textures/noise/grainy2.png");
    public static final ResourceLocation MANIFOLD = new ResourceLocation(ShaderedMod.MODID, "textures/noise/manifold.png");
    public static final ResourceLocation MILKY = new ResourceLocation(ShaderedMod.MODID, "textures/noise/milky.png");
    public static final ResourceLocation SWIRL = new ResourceLocation(ShaderedMod.MODID, "textures/noise/swirl.png");

    public static final ResourceLocation PIXEL = new ResourceLocation(ShaderedMod.MODID, "textures/environment/pixel.png");

    public static SkyblockBatchData spaceData = new SkyblockBatchData();
    public static SkyblockBatchData stormyData = new SkyblockBatchData();
    public static SkyblockBatchData oceanData = new SkyblockBatchData();
    public static SkyblockBatchData endData = new SkyblockBatchData();
    public static SkyblockBatchData endSkyData = new SkyblockBatchData();
    public static SkyblockBatchData eclipseData = new SkyblockBatchData();
    public static SkyblockBatchData iridiaData = new SkyblockBatchData();
    public static SkyblockBatchData forestData = new SkyblockBatchData();
    public static SkyblockBatchData lightData = new SkyblockBatchData();
    public static SkyblockBatchData mimicData = new SkyblockBatchData();

    public static SkyblockBatchData glassData = new SkyblockBatchData();
    public static GlassRenderer glassRenderer = new GlassRenderer(glassData);
    public static GlassChannel channel = new GlassChannel(new BlockPos(0, 128, 0));

    public static void setup() {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        Vector3f skyboxRotationSpeed = new Vector3f(0.007f, 0.01f, 0.004f);
        SkyblockRenderPass spaceRenderPass = new SkyblockRenderPass(NEBULA, spaceData,
                new SkyboxTranslation(), skyboxRotationSpeed, GameRenderer::getPositionTexColorShader
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, spaceRenderPass);

        SkyblockRenderPass stormyRenderPass = new SkyblockRenderPass(STORMY, stormyData,
                new SkyboxTranslation()
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, stormyRenderPass);

        SkyblockRenderPass oceanRenderPass = new SkyblockRenderPass(OCEAN, oceanData,
                new SkyboxTranslation()
                        .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                        .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, oceanRenderPass);

        SkyblockRenderPass eclipseRenderPass = new SkyblockRenderPass(ECLIPSE, eclipseData,
                new SkyboxTranslation(), skyboxRotationSpeed, () -> RegisterShaders.skyboxWarp
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, eclipseRenderPass);

        SkyblockRenderPass iridiaRenderPass = new SkyblockRenderPass(IRIDIA, iridiaData,
                new SkyboxTranslation()
                        .setTopBottomRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CW)
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, iridiaRenderPass);

        EndSkySkyblockRenderPass endSkyRenderPass = new EndSkySkyblockRenderPass(endSkyData);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, endSkyRenderPass);

        EndBlockRenderPass endBlockRenderPass = new EndBlockRenderPass(endData);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, endBlockRenderPass);

        SkyblockRenderPass forestRenderPass = new SkyblockRenderPass(FOREST, forestData,
                new SkyboxTranslation()
                        .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                        .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, forestRenderPass);

        SkyblockRenderPass lightRenderPass = new SkyblockRenderPass(LIGHT, lightData,
                new SkyboxTranslation()
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, lightRenderPass);

        MimicSkyblockRenderPass mimicSkyblockRenderPass = new MimicSkyblockRenderPass(
                mimicData
        );
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, mimicSkyblockRenderPass);


        renderer.add(RenderStage.AFTER_LEVEL, glassRenderer);
        glassRenderer.addGlassChannel(channel);
    }

    public static void preRender() {
        int grainy2 = getTextureId(GRAINY2);
        RegisterShaders.skyboxWarp.setSampler("Noise", grainy2);

        int pixel = getTextureId(PIXEL);
        RegisterShaders.skyblockScreen.setSampler("Pixel", pixel);

        RegisterShaders.skyblockBackground.setSampler("MainDepth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
    }

    public static int getTextureId(ResourceLocation resourceLocation) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(resourceLocation);
        return abstracttexture.getId();
    }
}