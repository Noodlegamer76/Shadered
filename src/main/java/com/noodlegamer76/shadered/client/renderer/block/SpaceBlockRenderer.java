package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.client.assimp.McModel;
import com.noodlegamer76.shadered.client.assimp.load.AssimpModels;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.entity.block.SpaceBlockEntity;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class SpaceBlockRenderer<T extends RenderTester> implements BlockEntityRenderer<SpaceBlockEntity> {

    public SpaceBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpaceBlockEntity pBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockRenderer.spaceData.add(pBlockEntity.getPass(), pBlockEntity.getBlockPos(), poseStack.last().pose(), false, 1.0F);
    }

    @Override
    public boolean shouldRender(SpaceBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
