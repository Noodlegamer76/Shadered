package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.event.RegisterShaders;
import com.noodlegamer76.shadered.mixin.GameRendererAccessor;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11C.*;

public class GlassRenderer implements RenderableComplexPass {
    private final SkyblockBatchData batchData;
    private final List<GlassChannel> glassChannels = new ArrayList<>();

    public GlassRenderer(SkyblockBatchData batchData) {
        this.batchData = batchData;
    }

    public SkyblockBatchData getBatchData() {
        return batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        if (batchData.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();
        TextureTarget readTarget = renderer.getRenderBuffer();
        TextureTarget portal = renderer.getWriteBuffer();
        TextureTarget extra = renderer.getExtraBuffer();
        LevelRenderer levelRenderer = mc.levelRenderer;
        GameRenderer gameRenderer = mc.gameRenderer;
        int width = mc.getWindow().getWidth();
        int height = mc.getWindow().getHeight();
        long nanos = Util.getNanos();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();

        for (GlassChannel channel : glassChannels) {
            GameRendererAccessor accessor = (GameRendererAccessor) gameRenderer;

            Vec3 portalOffset = channel.getOffset(camera);

            poseStack = new PoseStack();

            poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));

            GlUtils.copyColorFrom(portal, mc.getMainRenderTarget());
            portal.copyDepthFrom(mc.getMainRenderTarget());

            Entity cameraEntity = camera.getEntity();
            double oldX = cameraEntity.getX();
            double oldY = cameraEntity.getY();
            double oldZ = cameraEntity.getZ();
            cameraEntity.setPos(cameraPos.x + portalOffset.x, cameraPos.y + portalOffset.y, cameraPos.z + portalOffset.z);

            camera.setup(mc.level, camera.getEntity(), camera.isDetached(), false, 1.0f);

            double fov = accessor.shadered$invokeGetFov(camera, partialTick, true);
            Matrix4f projectionBackup = new Matrix4f(RenderSystem.getProjectionMatrix());

            portal.bindWrite(true);
            float[] fogColor = RenderSystem.getShaderFogColor();
            RenderSystem.clearColor(fogColor[0], fogColor[1], fogColor[2], 1.0F);
            RenderSystem.clear(16640, Minecraft.ON_OSX);

            mc.getMainRenderTarget().bindWrite(true);
            PoseStack projectionStack = new PoseStack();

            projectionStack.mulPoseMatrix(mc.gameRenderer.getProjectionMatrix(fov));

            accessor.shadered$invokeBobHurt(projectionStack, partialTick);

            if (mc.options.bobView().get()) {
                accessor.shadered$invokeBobView(projectionStack, partialTick);
            }

            if (mc.player != null) {
                float f = mc.options.screenEffectScale().get().floatValue();
                float f1 = Mth.lerp(partialTick, mc.player.oSpinningEffectIntensity, mc.player.spinningEffectIntensity) * f * f;
                if (f1 > 0.0F) {
                    int i = mc.player.hasEffect(MobEffects.CONFUSION) ? 7 : 20;
                    float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
                    f2 *= f2;
                    Axis axis = Axis.of(new Vector3f(0.0F, Mth.SQRT_OF_TWO / 2.0F, Mth.SQRT_OF_TWO / 2.0F));
                    projectionStack.mulPose(axis.rotationDegrees(((float)renderTick + partialTick) * (float)i));
                    projectionStack.scale(1.0F / f2, 1.0F, 1.0F);
                    float f3 = -((float)renderTick + partialTick) * (float)i;
                    projectionStack.mulPose(axis.rotationDegrees(f3));
                }
            }

            Matrix4f finalProjection = projectionStack.last().pose();

            RenderSystem.setProjectionMatrix(finalProjection, VertexSorting.DISTANCE_TO_ORIGIN);

            Matrix3f matrix3f = (new Matrix3f(poseStack.last().normal())).invert();
            RenderSystem.setInverseViewRotationMatrix(matrix3f);

            levelRenderer.prepareCullFrustum(
                    poseStack,
                    camera.getPosition(),
                    finalProjection
            );

            levelRenderer.renderLevel(
                    poseStack,
                    partialTick,
                    nanos,
                    false,
                    camera,
                    gameRenderer,
                    gameRenderer.lightTexture(),
                    projectionBackup
            );

            poseStack.pushPose();

            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

            cameraEntity.setPos(oldX, oldY, oldZ);
            RenderSystem.setProjectionMatrix(projectionBackup, VertexSorting.DISTANCE_TO_ORIGIN);

            RenderSystem.enableDepthTest();
            RenderSystem.setShader(RegisterShaders::getSkyblock);
            RegisterShaders.getSkyblock().setSampler("Skybox", portal.getColorTextureId());

            //for (GlassBatchData.BatchEntry entry : batchData.getBatchEntries()) {
            //    poseStack.pushPose();
            //    poseStack.translate(entry.boxStart().getX(), entry.boxStart().getY(), entry.boxStart().getZ());

            //    BlockPos glassPos = entry.glassPos();
            //    BlockPos startPos = entry.boxStart();
            //    BlockPos endPos = entry.boxEnd();

            //    RenderCube.renderSizedBox(
            //            new BlockPos[] {glassPos, startPos, endPos},
            //            partialTick, poseStack
            //    );

            //    poseStack.popPose();
            //}

            poseStack.popPose();

            GlUtils.copyColorFrom(extra, mc.getMainRenderTarget());
            extra.copyDepthFrom(mc.getMainRenderTarget());

            GlUtils.copyColorFrom(mc.getMainRenderTarget(), portal);
            mc.getMainRenderTarget().copyDepthFrom(portal);
            readTarget.bindWrite(true);


            poseStack = new PoseStack();

            poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));

            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();

            for (SkyblockPass pass : SkyblockPass.values()) {
                ShaderInstance shader = pass.getShader();
                shader.setSampler("Skybox", extra.getColorTextureId());
                shader.setSampler("PassDepth", readTarget.getDepthTextureId());

                RenderCube.renderSkyBlocks(batchData.get(pass), false, shader);
            }

            poseStack.popPose();
        }
        batchData.clear();
    }

    public void addGlassChannel(GlassChannel channel) {
        glassChannels.add(channel);
    }

    public List<GlassChannel> getGlassChannels() {
        return glassChannels;
    }
}
