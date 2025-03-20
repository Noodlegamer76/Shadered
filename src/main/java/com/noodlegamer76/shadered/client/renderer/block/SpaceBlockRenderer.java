package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.DynamicGeoBlockRenderer;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import com.noodlegamer76.shadered.entity.block.SpaceBlockEntity;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
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
import net.irisshaders.iris.targets.RenderTargets;
import net.irisshaders.iris.uniforms.IrisExclusiveUniforms;
import net.irisshaders.iris.uniforms.IrisInternalUniforms;
import net.irisshaders.iris.uniforms.IrisTimeUniforms;
import net.irisshaders.iris.uniforms.custom.CustomUniformFixedInputUniformsHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.awt.*;
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
            RenderTarget target = RenderEventsForRenderTargets.spaceTarget;
            VertexConsumer vertexConsumer = buffer.getBuffer(ModRenderTypes.SPACE_OCULUS);


            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();

            Vector3f[] normals = new Vector3f[] {
                    new Vector3f(0, 1, 0),   // Face 0: top
                    new Vector3f(0, 0, 1),   // Face 1
                    new Vector3f(0, -1, 0),  // Face 2: bottom
                    new Vector3f(0, 0, -1),  // Face 3
                    new Vector3f(1, 0, 0),   // Face 4
                    new Vector3f(-1, 0, 0)   // Face 5
            };

            for (int j = 0; j < 6; j++) {

                poseStack.pushPose();
                poseStack.translate(0.5, 0.5, 0.5);

                switch (j) {
                    case 1:
                        poseStack.mulPose(Axis.XP.rotationDegrees(90));
                        break;
                    case 2:
                        poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        break;
                    case 3:
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                        break;
                    case 4:
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                        break;
                    case 5:
                        poseStack.mulPose(Axis.ZN.rotationDegrees(-90));
                        break;
                    default:
                        break;
                }

                poseStack.translate(0, -0.5, 0);
                poseStack.scale(0.5f, 0.5f, 0.5f);

                Matrix4f matrix4f = poseStack.last().pose();
                Vector3f normal = normals[j];

                vertexConsumer.vertex(matrix4f, -1, 0, -1)
                        .color(Color.WHITE.getRGB())
                        .uv(0, 0)
                        .uv2(0, 0)
                        .normal(normal.x, normal.y, normal.z)
                        .endVertex();
                vertexConsumer.vertex(matrix4f, 1, 0, -1)
                        .color(Color.WHITE.getRGB())
                        .uv(1, 0)
                        .uv2(1, 0)
                        .normal(normal.x, normal.y, normal.z)
                        .endVertex();
                vertexConsumer.vertex(matrix4f, 1, 0, 1)
                        .color(Color.WHITE.getRGB())
                        .uv(1, 1)
                        .uv2(1, 1)
                        .normal(normal.x, normal.y, normal.z)
                        .endVertex();
                vertexConsumer.vertex(matrix4f, -1, 0, 1)
                        .color(Color.WHITE.getRGB())
                        .uv(0, 1)
                        .uv2(0, 1)
                        .normal(normal.x, normal.y, normal.z)
                        .endVertex();

                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRender(SpaceBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
