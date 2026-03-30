package com.noodlegamer76.shadered.client.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.client.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The reason for this class is to allow for rendering geometry with custom Core Shaders into a different FrameBuffer.
 * This allows the geometry to show up when using Shader Packs with Iris/Oculus.
 */
public class ComplexPassRenderer {
    private static final ComplexPassRenderer INSTANCE = new ComplexPassRenderer();

    public static ComplexPassRenderer getInstance() {
        return INSTANCE;
    }

    private ComplexPassRenderer() {
    }

    private final Map<RenderStage, List<RenderableComplexPass>> complexPasses = new HashMap<>();
    private TextureTarget renderBuffer;
    private TextureTarget writeBuffer;
    private boolean initialized;
    private int previousWidth;
    private int previousHeight;

    public void add(RenderStage stage, RenderableComplexPass effect) {
        complexPasses.computeIfAbsent(stage, s -> new ArrayList<>()).add(effect);
    }

    public Map<RenderStage, List<RenderableComplexPass>> getComplexPass() {
        return new HashMap<>(complexPasses);
    }

    public void init() {
        Window window = Minecraft.getInstance().getWindow();
        previousWidth = window.getWidth();
        previousHeight = window.getHeight();

        renderBuffer = new TextureTarget(previousWidth, previousHeight, true, Minecraft.ON_OSX);
        writeBuffer = new TextureTarget(previousWidth, previousHeight, true, Minecraft.ON_OSX);

        initialized = true;
    }

    private void preRender() {
        if (shouldResize()) {
            Window window = Minecraft.getInstance().getWindow();
            renderBuffer.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
            writeBuffer.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
        }

        renderToRenderTarget();
    }

    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        if (!initialized) {
            init();
        }

        preRender();

        TextureTarget current = renderBuffer;
        TextureTarget scratch = writeBuffer;

        List<RenderableComplexPass> passes = complexPasses.getOrDefault(stage, List.of());

        current.bindWrite(true);

        for (RenderableComplexPass pass : passes) {
            if (pass.getType() == PassType.GEOMETRY) {
                pass.render(stage, poseStack, renderTick, partialTick);
            }
        }

        for (RenderableComplexPass pass : passes) {
            if (pass.getType() == PassType.FILTER) {
                scratch.bindWrite(true);

                current.bindRead();

                pass.render(stage, poseStack, renderTick, partialTick);

                TextureTarget tmp = current;
                current = scratch;
                scratch = tmp;
            }
        }

        renderBuffer = current;
        writeBuffer = scratch;

        postRender();
    }

    private void postRender() {
        Window window = Minecraft.getInstance().getWindow();
        previousWidth = window.getWidth();
        previousHeight = window.getHeight();
        renderToMainTarget();

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }

    private void renderToRenderTarget() {
        RenderTarget mainTarget = Minecraft.getInstance().getMainRenderTarget();
        GlUtils.copyColorFrom(renderBuffer, mainTarget);
        renderBuffer.copyDepthFrom(mainTarget);
    }

    private void renderToMainTarget() {
        RenderTarget mainTarget = Minecraft.getInstance().getMainRenderTarget();
        GlUtils.copyColorFrom(mainTarget, renderBuffer);
        mainTarget.copyDepthFrom(renderBuffer);
    }

    public TextureTarget getRenderBuffer() {
        return renderBuffer;
    }

    public TextureTarget getWriteBuffer() {
        return writeBuffer;
    }

    public void clear() {
        complexPasses.clear();
    }

    public int getPreviousWidth() {
        return previousWidth;
    }

    public int getPreviousHeight() {
        return previousHeight;
    }

    public boolean shouldResize() {
        return previousWidth != Minecraft.getInstance().getWindow().getWidth() || previousHeight != Minecraft.getInstance().getWindow().getHeight();
    }
}
