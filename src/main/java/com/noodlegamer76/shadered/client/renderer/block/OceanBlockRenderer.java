package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.entity.block.EndBlockEntity;
import com.noodlegamer76.shadered.entity.block.OceanBlockEntity;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OceanBlockRenderer extends GeoBlockRenderer<OceanBlockEntity> {

    public OceanBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(ShaderedMod.MODID, "block")));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, OceanBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //if (!IrisApi.getInstance().isShaderPackInUse()) {
        //    RenderEventsForFbos.oceanPositions.add(animatable.getBlockPos());
        //    RenderEventsForFbos.oceanPose.add(poseStack.last().pose());
        //    return;
        //}
        //super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public boolean shouldRender(OceanBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
