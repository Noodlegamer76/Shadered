package com.noodlegamer76.shadered.client.renderer.bewlr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.model.BlockModel;
import com.noodlegamer76.shadered.client.renderer.ModRenderTypes;
import com.noodlegamer76.shadered.entity.block.SpaceBlockEntity;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

public class SpaceBlockItemRenderer extends BlockEntityWithoutLevelRenderer {
    EntityModelSet set;
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    public SpaceBlockItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        set = pEntityModelSet;
        this.blockEntityRenderDispatcher = pBlockEntityRenderDispatcher;

    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        VertexConsumer vertexConsumers = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(ModRenderTypes.SKYBOX);
        set.bakeLayer(BlockModel.LAYER_LOCATION).render(pPoseStack, vertexConsumers, pPackedLight, pPackedOverlay);
        //this.blockEntityRenderDispatcher.renderItem(new SpaceBlockEntity(BlockPos.ZERO, Blocks.CHEST.defaultBlockState()), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

    }
}
