package com.noodlegamer76.shadered.client.util;

import com.mojang.blaze3d.vertex.PoseStack;

public interface RenderableComplexPass {
    PassType getType();

    void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick);
}
