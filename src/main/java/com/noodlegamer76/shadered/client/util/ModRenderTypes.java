package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL33;

import java.util.HashMap;
import java.util.Map;

public class ModRenderTypes {
    protected static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
        );
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    protected static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
    protected static final RenderStateShard.DepthTestStateShard NO_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("always", 519);
    protected static final RenderStateShard.DepthTestStateShard EQUAL_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("==", 514);
    protected static final RenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("<=", 515);
    protected static final RenderStateShard.DepthTestStateShard GREATER_DEPTH_TEST = new RenderStateShard.DepthTestStateShard(">", 516);
    protected static final RenderStateShard.DepthTestStateShard LESS_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("<", GL33.GL_LESS);

    public static final RenderType WARP_TRANSPARENT = RenderType.create(
            "compressor",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            256,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> RegisterShaders.compressor))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(LESS_DEPTH_TEST)
                    .createCompositeState(false)
    );

    private static final Map<SkyblockType, Map<SkyblockPass, RenderType>> SKYBLOCK_RENDER_TYPES = new HashMap<>();

    public static RenderType getSkyblockRenderType(SkyblockType type, SkyblockPass pass) {
        return SKYBLOCK_RENDER_TYPES.get(type).get(pass);
    }

    private static int getSkyboxTextureId(SkyblockType type) {
        if (type == SkyblockType.SPACE) {
            return SkyblockRenderer.spaceRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.spaceRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.OCEAN) {
            return SkyblockRenderer.oceanRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.oceanRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.LIGHT) {
            return SkyblockRenderer.lightRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.lightRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.END) {
            return SkyblockRenderer.endBlockRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.endBlockRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.STORMY) {
            return SkyblockRenderer.stormyRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.stormyRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.ECLIPSE) {
            return SkyblockRenderer.eclipseRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.eclipseRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.MIMIC) {
            return SkyblockRenderer.mimicSkyblockRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.mimicSkyblockRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.END_SKY) {
            return SkyblockRenderer.endSkyRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.endSkyRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.IRIDIA) {
            return SkyblockRenderer.iridiaRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.iridiaRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        if (type == SkyblockType.FOREST) {
            return SkyblockRenderer.forestRenderPass.getSkyboxTarget() != null
                    ? SkyblockRenderer.forestRenderPass.getSkyboxTarget().getColorTextureId()
                    : 0;
        }

        ShaderedMod.LOGGER.error("Unknown skyblock type: {}", type);
        return 0;
    }

    static {
        for (SkyblockType type : SkyblockType.values()) {
            SKYBLOCK_RENDER_TYPES.put(type, new HashMap<>());

            for (SkyblockPass pass : SkyblockPass.values()) {
                String name = "shadered_skyblock_" + type.name().toLowerCase() + "_" + pass.name().toLowerCase();

                RenderStateShard.TexturingStateShard texturingStateShard = new RenderStateShard.TexturingStateShard(
                        name + "_texturing",
                        () -> {
                            int textureId = getSkyboxTextureId(type);
                            ShaderInstance shader = RegisterShaders.get(pass.shaderName);

                            if (shader != null) {
                                shader.setSampler("Skybox", textureId);
                            }

                            RenderSystem.setShaderTexture(0, textureId);
                        },
                        () -> {
                        }
                );

                RenderType renderType = RenderType.create(
                        name,
                        DefaultVertexFormat.NEW_ENTITY,
                        VertexFormat.Mode.QUADS,
                        256,
                        false,
                        false,
                        RenderType.CompositeState.builder()
                                .setShaderState(new RenderStateShard.ShaderStateShard(() -> RegisterShaders.get(pass.shaderName)))
                                .setTexturingState(texturingStateShard)
                                .setDepthTestState(LESS_DEPTH_TEST)
                                .createCompositeState(false)
                );

                SKYBLOCK_RENDER_TYPES.get(type).put(pass, renderType);
            }
        }
    }
}