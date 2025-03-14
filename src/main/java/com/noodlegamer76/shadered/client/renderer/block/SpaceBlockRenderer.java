package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.DynamicGeoBlockRenderer;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.entity.block.SpaceBlockEntity;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import com.noodlegamer76.shadered.event.RenderEventsForRenderTargets;
import com.noodlegamer76.shadered.mixin.IrisPipelineAccessor;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.gl.IrisRenderSystem;
import net.irisshaders.iris.gl.uniform.UniformHolder;
import net.irisshaders.iris.gl.uniform.UniformUpdateFrequency;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.pipeline.programs.ShaderKey;
import net.irisshaders.iris.samplers.IrisSamplers;
import net.irisshaders.iris.targets.RenderTarget;
import net.irisshaders.iris.targets.RenderTargets;
import net.irisshaders.iris.uniforms.IrisExclusiveUniforms;
import net.irisshaders.iris.uniforms.IrisInternalUniforms;
import net.irisshaders.iris.uniforms.IrisTimeUniforms;
import net.irisshaders.iris.uniforms.custom.CustomUniformFixedInputUniformsHolder;
import net.minecraft.client.Minecraft;
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

import java.util.ArrayList;
import java.util.List;

public class SpaceBlockRenderer implements BlockEntityRenderer<SpaceBlockEntity> {

    public SpaceBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpaceBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight, int pPackedOverlay) {
        //if (!IrisApi.getInstance().isShaderPackInUse() || true) {
        //    RenderEventsForFbos.spacePositions.add(animatable.getBlockPos());
        //    RenderEventsForFbos.spacePose.add(poseStack.last().pose());
        //    return;
        //}
        //super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (!Iris.isPackInUseQuick()) {
            RenderCube.renderSkyBlock(animatable.getBlockPos(), partialTick, poseStack.last().pose(), RenderEventsForRenderTargets.spaceTarget);
        }
        else {
            RenderCube.renderSkyBlock(animatable.getBlockPos(), partialTick, poseStack.last().pose(), RenderEventsForRenderTargets.spaceTarget);
        }
    }

    @Override
    public boolean shouldRender(SpaceBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
