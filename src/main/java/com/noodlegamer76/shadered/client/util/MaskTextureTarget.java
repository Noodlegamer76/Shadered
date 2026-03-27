package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL30;

public class MaskTextureTarget extends TextureTarget {
    public MaskTextureTarget(int width, int height) {
        super(width, height, false, Minecraft.ON_OSX);
    }

    @Override
    public void createBuffers(int width, int height, boolean clearError) {
        RenderSystem.assertOnRenderThreadOrInit();
        this.frameBufferId = GlStateManager.glGenFramebuffers();
        this.colorTextureId = GlStateManager._genTexture();
        GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
        GlStateManager._bindTexture(this.colorTextureId);
        GlStateManager._texImage2D(
                3553,
                0,
                GL30.GL_R32UI,
                width,
                height,
                0,
                GL30.GL_RED_INTEGER,
                GL30.GL_UNSIGNED_INT,
                null
        );
        GlStateManager._texParameter(3553, 10241, 9728);
        GlStateManager._texParameter(3553, 10240, 9728);
        GlStateManager._texParameter(3553, 10242, 33071);
        GlStateManager._texParameter(3553, 10243, 33071);
        GlStateManager._glFramebufferTexture2D(
                36160,
                36064,
                3553,
                this.colorTextureId,
                0
        );
        GlStateManager._glBindFramebuffer(36160, 0);
    }
}