package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
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

    public static final RenderType SPACE = getSkyboxRenderType(SkyblockRenderer.getInstance().getNebulaTarget());
    public static final RenderType OCEAN = getSkyboxRenderType(SkyblockRenderer.getInstance().getOceanTarget());
    public static final RenderType STORMY = getSkyboxRenderType(SkyblockRenderer.getInstance().getStormyTarget());
    public static final RenderType END_SKY = getSkyboxRenderType(SkyblockRenderer.getInstance().getEndSkyTarget());
    public static final RenderType ECLIPSE = getSkyboxRenderType(SkyblockRenderer.getInstance().getEclipseTarget());

    public static RenderType getSkyboxRenderType(TextureTarget textureTarget) {
        return RenderType.create(
                "skybox",
                DefaultVertexFormat.POSITION,
                VertexFormat.Mode.QUADS,
                256,
                true,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(() -> RegisterShadersEvent.skybox))
                        .setTextureState(new RenderStateShard.EmptyTextureStateShard(
                                () -> {
                                    ShaderInstance skybox = RegisterShadersEvent.skybox;
                                    skybox.setSampler("Skybox", textureTarget.getColorTextureId());
                                },
                                () -> {

                                }))
                        .createCompositeState(false)
        );
    }
}