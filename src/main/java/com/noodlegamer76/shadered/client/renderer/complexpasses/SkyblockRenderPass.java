package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.util.PassType;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.RenderableComplexPass;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.renderer.ShaderInstance;

import java.util.List;
import java.util.Map;

public class SkyblockRenderPass implements RenderableComplexPass {
    private final Map<SkyblockBatchData, Integer> batchData;

    public SkyblockRenderPass(Map<SkyblockBatchData, Integer> batchData) {
        this.batchData = batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();
        TextureTarget readTarget = renderer.getRenderBuffer();
        TextureTarget skyboxTarget = renderer.getWriteBuffer();

        for (Map.Entry<SkyblockBatchData, Integer> entry : batchData.entrySet()) {
            SkyblockBatchData data = entry.getKey();
            if (data.isEmpty()) continue;
            for (SkyblockPass pass : SkyblockPass.values()) {
                ShaderInstance shader = RegisterShaders.get(pass.getShaderName());
                shader.setSampler("Skybox", batchData.get(data));
                shader.setSampler("PassDepth", readTarget.getDepthTextureId());

                RenderCube.renderSkyBlocks(data.get(pass), false, shader);
            }

            data.clear();
        }
    }
}
