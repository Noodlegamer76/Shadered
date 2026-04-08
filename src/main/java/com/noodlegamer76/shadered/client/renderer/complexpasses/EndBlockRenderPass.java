package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.skyblock.SkyBoxRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

public class EndBlockRenderPass implements RenderableComplexPass {
    private final SkyblockBatchData batchData;

    public EndBlockRenderPass(SkyblockBatchData batchData) {
        this.batchData = batchData;
    }

    public SkyblockBatchData getBatchData() {
        return batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    //TODO: Make this work with shaders
    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        if (batchData.isEmpty()) return;
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();
        TextureTarget skyboxTarget = renderer.getWriteBuffer();

        skyboxTarget.bindWrite(true);
        SkyBoxRenderer.renderEndPortalSky(poseStack);

        renderer.getRenderBuffer().bindWrite(true);

        for (SkyblockPass pass : SkyblockPass.values()) {
            ShaderInstance shader = pass.getShader();
            shader.setSampler("Skybox", skyboxTarget.getColorTextureId());

            RenderCube.renderSkyBlocks(batchData.get(pass), false, shader);
        }

        batchData.clear();
    }
}
