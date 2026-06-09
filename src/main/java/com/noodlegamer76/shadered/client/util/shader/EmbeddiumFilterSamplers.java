package com.noodlegamer76.shadered.client.util.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class EmbeddiumFilterSamplers {
    private static final int FILTER_SAMPLER_UNIT = 9;
    private static final int FILTER_DEPTH_SAMPLER_UNIT = 10;
    private static final int MAIN_DEPTH_SAMPLER_UNIT = 11;

    public static void upload(int program) {
        if (program == 0) {
            return;
        }

        if (SkyblockRenderer.filterBlockComplexPass.getFilterTarget() == null) {
            return;
        }

        int filterColor = SkyblockRenderer.filterBlockComplexPass.getFilterTarget().getColorTextureId();
        int filterDepth = SkyblockRenderer.filterBlockComplexPass.getFilterTarget().getDepthTextureId();
        int mainDepth = Minecraft.getInstance().getMainRenderTarget().getDepthTextureId();

        bindSampler(program, "FilterSampler", FILTER_SAMPLER_UNIT, filterColor);
        bindSampler(program, "FilterDepthSampler", FILTER_DEPTH_SAMPLER_UNIT, filterDepth);
        bindSampler(program, "MainDepthSampler", MAIN_DEPTH_SAMPLER_UNIT, mainDepth);
    }

    private static void bindSampler(int program, String uniformName, int textureUnit, int textureId) {
        int location = GL20.glGetUniformLocation(program, uniformName);
        if (location < 0) {
            return;
        }

        GL20.glUniform1i(location, textureUnit);

        RenderSystem.activeTexture(GL13.GL_TEXTURE0 + textureUnit);
        RenderSystem.bindTexture(textureId);
        RenderSystem.activeTexture(GL13.GL_TEXTURE0);
    }
}