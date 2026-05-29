package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.skyblock.SkyBoxRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

public class EndSkySkyboxRenderPass implements RenderableComplexPass {
    private final SkyblockBatchData batchData;
    private TextureTarget skyboxTarget;

    public EndSkySkyboxRenderPass(SkyblockBatchData batchData) {
        this.batchData = batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        if (skyboxTarget == null) {
            skyboxTarget = new TextureTarget(renderer.getPreviousWidth(), renderer.getPreviousHeight(), false, Minecraft.ON_OSX);
            SkyblockRenderer.DATA_LIST.put(batchData, skyboxTarget.getColorTextureId());
        }
        else if (skyboxTarget.width != renderer.getPreviousWidth() || skyboxTarget.height != renderer.getPreviousHeight()) {
            skyboxTarget.resize(renderer.getPreviousWidth(), renderer.getPreviousHeight(), Minecraft.ON_OSX);
            SkyblockRenderer.DATA_LIST.put(batchData, skyboxTarget.getColorTextureId());
        }

        skyboxTarget.bindWrite(true);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        SkyBoxRenderer.renderEndSky(poseStack);

        renderer.getRenderBuffer().bindWrite(true);

        batchData.clear();
    }

    public TextureTarget getSkyboxTarget() {
        return skyboxTarget;
    }
}