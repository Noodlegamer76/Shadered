package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.world.level.Level;
import org.lwjgl.opengl.GL33;

public class ModRenderTypes {
    protected static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    protected static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
    protected static final RenderStateShard.DepthTestStateShard NO_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("always", 519);
    protected static final RenderStateShard.DepthTestStateShard EQUAL_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("==", 514);
    protected static final RenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("<=", 515);
    protected static final RenderStateShard.DepthTestStateShard GREATER_DEPTH_TEST = new RenderStateShard.DepthTestStateShard(">", 516);
    protected static final RenderStateShard.DepthTestStateShard LESS_DEPTH_TEST = new RenderStateShard.DepthTestStateShard(">", GL33.GL_LESS);
    protected static final RenderStateShard.LightmapStateShard LIGHTMAP = new RenderStateShard.LightmapStateShard(true);

    public static final RenderType SPACE = getSkyboxRenderType(SkyblockRenderer.getInstance().getNebulaTarget(), "space");
    public static final RenderType OCEAN = getSkyboxRenderType(SkyblockRenderer.getInstance().getOceanTarget(), "ocean");
    public static final RenderType STORMY = getSkyboxRenderType(SkyblockRenderer.getInstance().getStormyTarget(), "stormy");
    public static final RenderType END_SKY = getSkyboxRenderType(SkyblockRenderer.getInstance().getEndSkyTarget(), "end_sky");
    public static final RenderType ECLIPSE = getSkyboxRenderType(SkyblockRenderer.getInstance().getEclipseTarget(), "eclipse");
    public static final RenderType END_BLOCK = RenderType.create(
            "end",
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> RegisterShaders.endCopy))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                            .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                            .build())
                    .createCompositeState(false)
    );


    public static RenderType getSkyboxRenderType(TextureTarget textureTarget, String name) {
        return RenderType.create(
                name,
                DefaultVertexFormat.BLOCK,
                VertexFormat.Mode.QUADS,
                2097152,
                true,
                false,
                RenderType.CompositeState.builder()
                        .setLightmapState(LIGHTMAP)
                        .setShaderState(new RenderStateShard.ShaderStateShard(() -> RegisterShaders.skybox))
                        .setTextureState(new RenderStateShard.EmptyTextureStateShard(
                                () -> {
                                    ShaderInstance skybox = RegisterShaders.skybox;
                                    skybox.setSampler("Skybox", textureTarget.getColorTextureId());
                                },
                                () -> {

                                }))
                        .createCompositeState(true)
        );
    }
}