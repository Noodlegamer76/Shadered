package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.renderer.GameRenderer;
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

        RenderSystem.setShader(GameRenderer::getRendertypeEndPortalShader);
        RenderCubeAroundPlayer.renderCubeWithShader(new PoseStack());
    }
}
