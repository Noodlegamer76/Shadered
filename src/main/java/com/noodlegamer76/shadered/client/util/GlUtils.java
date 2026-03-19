package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

public class GlUtils {

    /**
     * Copies the color buffer from one RenderTarget to another.
     *
     * @param to The destination RenderTarget.
     * @param from The source RenderTarget.
     */
    public static void copyColorFrom(RenderTarget to, RenderTarget from) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._glBindFramebuffer(36008, from.frameBufferId);
        GlStateManager._glBindFramebuffer(36009, to.frameBufferId);
        GlStateManager._glBlitFrameBuffer(
                0, 0, from.width, from.height,
                0, 0, from.width, from.height,
                16384,
                9728
        );
        GlStateManager._glBindFramebuffer(36160, 0);
    }
}
