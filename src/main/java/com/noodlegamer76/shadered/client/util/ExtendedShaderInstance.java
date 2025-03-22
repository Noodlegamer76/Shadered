package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.joml.Matrix4f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtendedShaderInstance extends ShaderInstance {
    private static final List<ExtendedShaderInstance> shaders = new ArrayList<>();

    public static void setSamplers() {
        for (ExtendedShaderInstance shader : shaders) {
            shader.setSampler("Color", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());

            shader.setSampler("Depth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());

            shader.setSampler("Grainy", getIdFromTexture(new ResourceLocation(ShaderedMod.MODID, "textures/noise/grainy.png")));
            shader.setSampler("Manifold", getIdFromTexture(new ResourceLocation(ShaderedMod.MODID, "textures/noise/manifold.png")));
            shader.setSampler("Milky", getIdFromTexture(new ResourceLocation(ShaderedMod.MODID, "textures/noise/milky.png")));
            shader.setSampler("Swirl", getIdFromTexture(new ResourceLocation(ShaderedMod.MODID, "textures/noise/swirl.png")));
        }
    }

    private static int getIdFromTexture(ResourceLocation texture) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(texture);
        return abstracttexture.getId();
    }

    public static void setUniforms() {
        for (ExtendedShaderInstance shader : shaders) {
            Uniform inverseProjMat = shader.getUniform("InverseProjMat");
            if (inverseProjMat != null) {
                inverseProjMat.set(new Matrix4f(RenderSystem.getProjectionMatrix()).invert());
            }

            Uniform inverseModelViewMat = shader.getUniform("InverseModelViewMat");
            if (inverseModelViewMat != null) {
                inverseModelViewMat.set(new Matrix4f(RenderSystem.getModelViewMatrix()).invert());
            }

            Uniform cameraPos = shader.getUniform("CameraPos");
            if (cameraPos != null) {
                cameraPos.set(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().toVector3f());
            }
        }
    }

    public ExtendedShaderInstance(ResourceProvider pResourceProvider, ResourceLocation shaderLocation, VertexFormat pVertexFormat) throws IOException {
        super(pResourceProvider, shaderLocation, pVertexFormat);
        shaders.add(this);
    }

    public static List<ExtendedShaderInstance> getShaders() {
        return shaders;
    }
}
