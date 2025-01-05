package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

import static com.noodlegamer76.shadered.event.RegisterShadersEvent.*;

public class ModRenderTypes {
    protected static final RenderStateShard.OverlayStateShard OVERLAY = new RenderStateShard.OverlayStateShard(true);
    protected static final RenderStateShard.OverlayStateShard NO_OVERLAY = new RenderStateShard.OverlayStateShard(false);

    public static final RenderType TEST_RENDERER = RenderType.create(
            "test",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> RegisterShadersEvent.TEST))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(TheEndPortalRenderer.END_SKY_LOCATION,
                            false, false).add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false).build())
                    .createCompositeState(true)
    );

    public static final RenderType SKYBOX = RenderType.create(
            "skybox",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> skybox))
                    .createCompositeState(true)
    );


    public static final RenderType SPACE = RenderType.create(
            "space",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> spaceShader))
                    .createCompositeState(true)
    );


    public static final RenderType OCEAN = RenderType.create(
            "ocean",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> oceanShader))
                    .createCompositeState(true)
    );


    public static final RenderType STORMY = RenderType.create(
            "stormy",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> stormyShader))
                    .createCompositeState(true)
    );


    public static final RenderType END_SKY = RenderType.create(
            "end_sky",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> endSkyShader))
                    .createCompositeState(true)
    );
}
