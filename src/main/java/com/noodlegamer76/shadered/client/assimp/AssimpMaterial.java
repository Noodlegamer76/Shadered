package com.noodlegamer76.shadered.client.assimp;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.assimp.util.BlendMode;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import static com.noodlegamer76.shadered.client.assimp.util.BlendMode.*;

public class AssimpMaterial {
    private static final ResourceLocation WHITE_TEXTURE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
    private static final ResourceLocation DEFAULT_EMISSIVE = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/environment/emissive.png");
    private static final ResourceLocation DEFAULT_NORMAL = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/environment/normal.png");
    private static final ResourceLocation DEFAULT_SPECULAR = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/environment/specular.png");
    private static final ResourceLocation DEFAULT_HEIGHT = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/environment/height.png");
    private ResourceLocation diffuseTexture = WHITE_TEXTURE;
    private ResourceLocation specularTexture = DEFAULT_SPECULAR;
    private ResourceLocation normalTexture = DEFAULT_NORMAL;
    private ResourceLocation emissiveTexture = DEFAULT_EMISSIVE;
    private ResourceLocation heightTexture = DEFAULT_HEIGHT;
    private float r, g, b, a = 1.0f;
    private boolean isTransparent = false;
    private BlendMode mode = OPAQUE;

    public AssimpMaterial() {
    }

    public void setDiffuseTexture(ResourceLocation diffuseTexture) {
        this.diffuseTexture = (diffuseTexture == null || diffuseTexture.getPath().contains("missingno"))
                ? WHITE_TEXTURE
                : diffuseTexture;
    }

    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ResourceLocation getDiffuseTexture() {
        return diffuseTexture;
    }

    public void setTransparent(boolean transparent) {
        this.isTransparent = transparent;
    }

    public boolean useAlpha() {
        return isTransparent;
    }

    public void setSpecularTexture(ResourceLocation specularTexture) {
        this.specularTexture = (specularTexture == null || specularTexture.getPath().contains("missingno"))
                ? DEFAULT_SPECULAR
                : specularTexture;
    }

    public ResourceLocation getSpecularTexture() {
        return specularTexture;
    }

    public void setNormalTexture(ResourceLocation normalTexture) {
        this.normalTexture = (normalTexture == null || normalTexture.getPath().contains("missingno"))
                ? DEFAULT_NORMAL
                : normalTexture;
    }

    public ResourceLocation getNormalTexture() {
        return normalTexture;
    }

    public void setEmissiveTexture(ResourceLocation emissiveTexture) {
        this.emissiveTexture = (emissiveTexture == null || emissiveTexture.getPath().contains("missingno"))
                ? DEFAULT_EMISSIVE
                : emissiveTexture;
    }

    public void setHeightTexture(ResourceLocation heightTexture) {
        this.heightTexture = (heightTexture == null || heightTexture.getPath().contains("missingno"))
                ? DEFAULT_HEIGHT
                : heightTexture;
    }


    public ResourceLocation getEmissiveTexture() {
        return emissiveTexture;
    }

    public float red() {
        return r;
    }

    public float green() {
        return g;
    }

    public float blue() {
        return b;
    }

    public float alpha() {
        return a;
    }

    public BlendMode getBlendMode() {
        return mode;
    }

    public void setBlendMode(BlendMode mode) {
        this.mode = mode;
    }

    public void bind() {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        RenderSystem.setShaderColor(r, g, b, a);
        applyBlendState();
        shader.setSampler("diffuse", SkyblockRenderer.getTextureId(diffuseTexture));
        shader.setSampler("normal", SkyblockRenderer.getTextureId(normalTexture));
        shader.setSampler("pbr", SkyblockRenderer.getTextureId(specularTexture));
        shader.setSampler("emissive", SkyblockRenderer.getTextureId(emissiveTexture));
        shader.setSampler("height", SkyblockRenderer.getTextureId(heightTexture));
    }

    public void applyBlendState() {
        switch (getBlendMode()) {
            case OPAQUE -> {
                RenderSystem.disableBlend();
            }
            case ALPHA -> {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
            }
            case ADDITIVE -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            }
        }
    }
}