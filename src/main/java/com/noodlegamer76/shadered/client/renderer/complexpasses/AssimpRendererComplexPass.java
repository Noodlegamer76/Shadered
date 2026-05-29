package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.assimp.AssimpRenderer;
import com.noodlegamer76.shadered.client.util.PassType;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.RenderableComplexPass;

public class AssimpRendererComplexPass implements RenderableComplexPass {
    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        AssimpRenderer renderer = AssimpRenderer.getInstance();
        renderer.render(partialTick, renderTick);
    }
}
