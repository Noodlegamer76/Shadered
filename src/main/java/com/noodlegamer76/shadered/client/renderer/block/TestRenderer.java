package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class TestRenderer<T extends RenderTester> implements BlockEntityRenderer<RenderTester> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/block/stone.png");

    public TestRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RenderTester pBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        RenderSystem.setShader(() -> RegisterShadersEvent.TEST);

        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(matrix4f, 0, 0, 0).uv(1.0F, 0.0F).color(255, 255, 0, 255).endVertex();
        bufferbuilder.vertex(matrix4f, 0, 1, 0).uv(0.0F, 1.0F).color(255, 0, 255, 255).endVertex();
        bufferbuilder.vertex(matrix4f, 1, 1, 0).uv(1.0F, 1.0F).color(0, 255, 255, 255).endVertex();
        bufferbuilder.vertex(matrix4f, 1, 0, 0).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
        tesselator.end();
    }
}
