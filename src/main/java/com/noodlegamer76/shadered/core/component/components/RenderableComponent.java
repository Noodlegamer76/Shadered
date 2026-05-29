package com.noodlegamer76.shadered.core.component.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.client.renderer.MultiBufferSource;

public interface RenderableComponent {
    void render(GameObject entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight);
}
