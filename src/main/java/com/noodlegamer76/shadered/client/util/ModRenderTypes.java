package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyBoxRenderer;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL33;

import static net.minecraft.client.renderer.RenderStateShard.*;
import static net.minecraft.client.renderer.RenderStateShard.OVERLAY;

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

    private static final RenderStateShard.TexturingStateShard SPACE_TEXTURING = new RenderStateShard.TexturingStateShard("space_texturing",
            () -> RenderSystem.setShaderTexture(0, SkyblockRenderer.spaceRenderPass.getSkyboxTarget().getColorTextureId()),
            () -> {}
            );

    private static final RenderStateShard.TexturingStateShard STORMY_TEXTURING = new RenderStateShard.TexturingStateShard("stormy_texturing",
            () -> RenderSystem.setShaderTexture(0, SkyblockRenderer.stormyRenderPass.getSkyboxTarget().getColorTextureId()),
            () -> {}
    );

    private static final RenderStateShard.TexturingStateShard WINDOW_TEXTURING = new RenderStateShard.TexturingStateShard("window_texturing",
            () -> RenderSystem.setShaderTexture(0, SkyblockRenderer.paintingWindow.getColorTextureId()),
            () -> {}
    );

    private static final RenderStateShard.OutputStateShard SKYBOX_OUTPUT = new RenderStateShard.OutputStateShard(
            "skybox_output",
            () -> {
                SkyblockRenderer.paintingWindow.bindWrite(true);
            },
            () -> {
                Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
            }
    );

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

    public static final RenderType SPACE = RenderType.create(
            "space",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_CUTOUT_SHADER)
                    .setOutputState(SKYBOX_OUTPUT)
                    .setTexturingState(SPACE_TEXTURING)
                    .setDepthTestState(LESS_DEPTH_TEST)
                    .createCompositeState(false)
    );

    public static final RenderType STORMY = RenderType.create(
            "stormy",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_CUTOUT_SHADER)
                    .setTexturingState(STORMY_TEXTURING)
                    .setOutputState(SKYBOX_OUTPUT)
                    .setDepthTestState(LESS_DEPTH_TEST)
                    .createCompositeState(false)
    );

    public static final RenderType ENTITY_IN_WINDOW = RenderType.create(
            "entity_in_window",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_SOLID_SHADER)
                    .setOutputState(SKYBOX_OUTPUT)
                    .setTransparencyState(NO_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true)
    );

    public static final RenderType WINDOW = RenderType.create(
            "window",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(POSITION_TEX_SHADER)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTexturingState(WINDOW_TEXTURING)
                    .setDepthTestState(LESS_DEPTH_TEST)
                    .createCompositeState(false)
    );
}
