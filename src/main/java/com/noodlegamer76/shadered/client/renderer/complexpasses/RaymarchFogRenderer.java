package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.PassType;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.RenderableComplexPass;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RaymarchFogRenderer implements RenderableComplexPass {
    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        //temperary disable
        if (true) {
            return;
        }
        float densityMultiplier = 0.065f;
        int steps = 32;
        float amplitude = 0.5f;
        float frequency = 5.0f;

        ShaderInstance shader = RegisterShaders.getRaymarchFog();
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();

        PoseStack viewMat = new PoseStack();
        viewMat.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        viewMat.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));

        RenderSystem.setShader(() -> shader);

        Uniform cameraPosUniform = shader.getUniform("CameraPos");
        if (cameraPosUniform != null) {
            cameraPosUniform.set((float) cameraPos.x, (float) cameraPos.y, (float) cameraPos.z);
        }

        Uniform viewMatUniform = shader.getUniform("ViewMat");
        if (viewMatUniform != null) {
            viewMatUniform.set(viewMat.last().pose());
        }

        Uniform cameraPosInteger = shader.getUniform("CameraPosInteger");
        if (cameraPosInteger != null) {
            cameraPosInteger.set((int) cameraPos.x, (int) cameraPos.y, (int) cameraPos.z);
        }

        Uniform densityMultiplierUniform = shader.getUniform("DensityMultiplier");
        if (densityMultiplierUniform != null) {
            densityMultiplierUniform.set(densityMultiplier);
        }

        Uniform stepsUniform = shader.getUniform("Steps");
        if (stepsUniform != null) {
            stepsUniform.set(steps);
        }

        Uniform amplitudeUniform = shader.getUniform("Amplitude");
        if (amplitudeUniform != null) {
            amplitudeUniform.set(amplitude);
        }

        Uniform frequencyUniform = shader.getUniform("Frequency");
        if (frequencyUniform != null) {
            frequencyUniform.set(frequency);
        }

        Uniform sunDirectionUniform = shader.getUniform("SunDirection");
        if (sunDirectionUniform != null) {
            sunDirectionUniform.set(getSunDirection(partialTick).toVector3f());
        }

        shader.setSampler("MainDepth", mc.getMainRenderTarget().getDepthTextureId());
        shader.setSampler("MainColor", mc.getMainRenderTarget().getColorTextureId());
        shader.setSampler("BallTexture", SkyblockRenderer.eclipseRenderPass.getSkyboxTarget().getColorTextureId());
        shader.setSampler("SpaceTexture", SkyblockRenderer.spaceRenderPass.getSkyboxTarget().getColorTextureId());

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        bufferBuilder.addVertex(-1, -1, 0);
        bufferBuilder.addVertex(1, -1, 0);
        bufferBuilder.addVertex(1, 1, 0);
        bufferBuilder.addVertex(-1, 1, 0);

        MeshData meshData = bufferBuilder.build();
        if (meshData != null) {
            BufferUploader.drawWithShader(meshData);
        }
    }

    public static Vec3 getSunDirection(float partialTicks) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return Vec3.ZERO;
        float celestialAngle = Minecraft.getInstance().level.getSunAngle(partialTicks);
        float theta = celestialAngle * ((float)Math.PI * 2F);

        double x = 0.0F;
        double y = -Math.cos(theta);
        double z = -Math.sin(theta);

        return new Vec3(x, y, z);
    }
}
