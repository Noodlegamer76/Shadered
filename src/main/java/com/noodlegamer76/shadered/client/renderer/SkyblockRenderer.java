package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.complexpasses.*;
import com.noodlegamer76.shadered.client.util.GlUtils;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import com.noodlegamer76.shadered.client.util.shader.EmbeddiumFilterSamplers;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatchRegistry;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyboxTranslation;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL43;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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

    public static SkyblockBatchData filterData = new SkyblockBatchData();

    public static Map<SkyblockBatchData, Integer> DATA_LIST = new HashMap<>();

    public static SkyblockBatchData glassData = new SkyblockBatchData();
    public static GlassRenderer glassRenderer = new GlassRenderer(glassData);
    public static GlassChannel channel = new GlassChannel(new BlockPos(0, 128, 0));


    public static final Vector3f skyboxRotationSpeed = new Vector3f(0.007f, 0.01f, 0.004f);
    public static final SkyboxRenderPass spaceRenderPass = new SkyboxRenderPass(NEBULA, spaceData,
            new SkyboxTranslation(), skyboxRotationSpeed, GameRenderer::getPositionTexColorShader
    );

    public static final SkyboxRenderPass stormyRenderPass = new SkyboxRenderPass(STORMY, stormyData,
            new SkyboxTranslation()
    );
    public static final SkyboxRenderPass oceanRenderPass = new SkyboxRenderPass(OCEAN, oceanData,
            new SkyboxTranslation()
                    .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                    .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
    );
    public static final SkyboxRenderPass eclipseRenderPass = new SkyboxRenderPass(ECLIPSE, eclipseData,
            new SkyboxTranslation(), skyboxRotationSpeed, () -> RegisterShaders.skyboxWarp
    );
    public static final SkyboxRenderPass iridiaRenderPass = new SkyboxRenderPass(IRIDIA, iridiaData,
            new SkyboxTranslation()
                    .setTopBottomRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CW)
    );
    public static final EndSkySkyboxRenderPass endSkyRenderPass = new EndSkySkyboxRenderPass(endSkyData);
    public static final EndBoxRenderPass endBlockRenderPass = new EndBoxRenderPass(endData);
    public static final SkyboxRenderPass forestRenderPass = new SkyboxRenderPass(FOREST, forestData,
            new SkyboxTranslation()
                    .setAllFlip(SkyboxTranslation.SkyboxFlip.NONE)
                    .setAllRot(SkyboxTranslation.SkyboxRotation.ROTATE_90_CCW)
    );
    public static final SkyboxRenderPass lightRenderPass = new SkyboxRenderPass(LIGHT, lightData,
            new SkyboxTranslation()
    );
    public static final MimicSkyboxRenderPass mimicSkyblockRenderPass = new MimicSkyboxRenderPass(
            mimicData
    );
    public static final FilterBlockEncodeComplexPass filterBlockComplexPass = new FilterBlockEncodeComplexPass(filterData);
    public static final FilterBlockApplyComplexPass filterBlockApplyComplexPass = new FilterBlockApplyComplexPass();


    public static SkyblockBatchData getData(SkyblockType type) {
        if (type == SkyblockType.SPACE) {
            return SkyblockRenderer.spaceData;
        }
        else if (type == SkyblockType.OCEAN) {
            return SkyblockRenderer.oceanData;
        }
        else if (type == SkyblockType.LIGHT) {
            return SkyblockRenderer.lightData;
        }
        else if (type == SkyblockType.END) {
            return SkyblockRenderer.endData;
        }
        else if (type == SkyblockType.STORMY) {
            return SkyblockRenderer.stormyData;
        }
        else if (type == SkyblockType.ECLIPSE) {
            return SkyblockRenderer.eclipseData;
        }
        else if (type == SkyblockType.MIMIC) {
            return SkyblockRenderer.mimicData;
        }
        else if (type == SkyblockType.END_SKY) {
            return SkyblockRenderer.endSkyData;
        }
        else if (type == SkyblockType.IRIDIA) {
            return SkyblockRenderer.iridiaData;
        }
        else if (type == SkyblockType.FOREST) {
            return SkyblockRenderer.forestData;
        }
        else {
            return SkyblockRenderer.stormyData;
        }
    }

    public static void setup() {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        renderer.add(RenderStage.AFTER_SKY, spaceRenderPass);
        renderer.add(RenderStage.AFTER_SKY, stormyRenderPass);
        renderer.add(RenderStage.AFTER_SKY, oceanRenderPass);
        renderer.add(RenderStage.AFTER_SKY, eclipseRenderPass);
        renderer.add(RenderStage.AFTER_SKY, iridiaRenderPass);
        renderer.add(RenderStage.AFTER_SKY, endSkyRenderPass);
        renderer.add(RenderStage.AFTER_SKY, endBlockRenderPass);
        renderer.add(RenderStage.AFTER_SKY, forestRenderPass);
        renderer.add(RenderStage.AFTER_SKY, lightRenderPass);
        renderer.add(RenderStage.AFTER_SKY, mimicSkyblockRenderPass);

        SkyblockRenderPass skyblockRenderPass = new SkyblockRenderPass(DATA_LIST);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, skyblockRenderPass);

        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, filterBlockComplexPass);
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, filterBlockApplyComplexPass);


        renderer.add(RenderStage.AFTER_LEVEL, glassRenderer);
        glassRenderer.addGlassChannel(channel);

        AssimpRendererComplexPass assimpRendererComplexPass = new AssimpRendererComplexPass();
        renderer.add(RenderStage.AFTER_BLOCK_ENTITIES, assimpRendererComplexPass);
    }

    public static void preRender() {
        int grainy2 = getTextureId(GRAINY2);
        RegisterShaders.skyboxWarp.setSampler("Noise", grainy2);

        int pixel = getTextureId(PIXEL);
        RegisterShaders.skyblockScreen.setSampler("Pixel", pixel);

        RegisterShaders.skyblockBackground.setSampler("MainDepth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());

        setupFilterSamplersForLitShaders();
    }

    private static void setupFilterSamplersForLitShaders() {
        if (filterBlockComplexPass.getFilterTarget() == null) {
            return;
        }

        TextureTarget extraBuffer = ComplexPassRenderer.getInstance().getExtraBuffer();
        extraBuffer.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        GL43.glMemoryBarrier(
                GL43.GL_FRAMEBUFFER_BARRIER_BIT |
                        GL43.GL_TEXTURE_FETCH_BARRIER_BIT
        );

        int filterColor = filterBlockComplexPass.getFilterTarget().getColorTextureId();
        int filterDepth = filterBlockComplexPass.getFilterTarget().getDepthTextureId();
        int mainDepth = extraBuffer.getDepthTextureId();

        for (Supplier<ShaderInstance> shaderSupplier : FILTERED_LIT_SHADER_SUPPLIERS) {
            ShaderInstance shader = shaderSupplier.get();
            if (shader == null) {
                continue;
            }

            shader.setSampler("FilterSampler", filterColor);
            shader.setSampler("FilterDepthSampler", filterDepth);
            shader.setSampler("MainDepthSampler", mainDepth);
        }
    }

    private static final List<Supplier<ShaderInstance>> FILTERED_LIT_SHADER_SUPPLIERS = List.of(
            GameRenderer::getRendertypeEntitySolidShader,
            GameRenderer::getRendertypeEntityCutoutShader,
            GameRenderer::getRendertypeEntityTranslucentShader,
            GameRenderer::getRendertypeEntityCutoutNoCullShader,
            GameRenderer::getRendertypeLeashShader,
            GameRenderer::getRendertypeEntitySmoothCutoutShader,
            GameRenderer::getRendertypeEntityTranslucentCullShader,
            GameRenderer::getRendertypeItemEntityTranslucentCullShader,
            GameRenderer::getRendertypeEntityCutoutNoCullZOffsetShader,
            GameRenderer::getRendertypeCutoutMippedShader,
            GameRenderer::getRendertypeSolidShader,
            GameRenderer::getParticleShader,
            GameRenderer::getRendertypeCutoutShader,
            GameRenderer::getPositionTexColorNormalShader,
            GameRenderer::getRendertypeArmorCutoutNoCullShader,
            GameRenderer::getRendertypeEntityDecalShader,
            GameRenderer::getRendertypeEntityNoOutlineShader,
            GameRenderer::getRendertypeEntityShadowShader,
            GameRenderer::getRendertypeEntityTranslucentEmissiveShader,
            GameRenderer::getRendertypeOutlineShader,
            GameRenderer::getRendertypeTranslucentShader,
            GameRenderer::getRendertypeTripwireShader
    );

    public static int getTextureId(ResourceLocation resourceLocation) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(resourceLocation);
        return abstracttexture.getId();
    }
}