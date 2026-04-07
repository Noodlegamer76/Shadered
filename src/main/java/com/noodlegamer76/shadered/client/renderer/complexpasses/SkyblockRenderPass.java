package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.skyblock.SkyBoxRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.client.util.skyblock.SkyboxTranslation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SkyblockRenderPass implements RenderableComplexPass {
    private final ResourceLocation texturePath;
    private final SkyblockBatchData batchData;
    private final SkyboxTranslation translation;
    private final Vector3f skyboxRotationSpeed;
    private final Supplier<ShaderInstance> skyboxShader;

    public SkyblockRenderPass(ResourceLocation texturePath, SkyblockBatchData batchData, SkyboxTranslation translation, Vector3f skyboxRotationSpeed, Supplier<ShaderInstance> skyboxShader) {
        this.texturePath = texturePath;
        this.batchData = batchData;
        this.translation = translation;
        this.skyboxRotationSpeed = skyboxRotationSpeed;
        this.skyboxShader = skyboxShader;
    }

    public SkyblockRenderPass(ResourceLocation texturePath, SkyblockBatchData batchData, SkyboxTranslation translation) {
        this(texturePath, batchData, translation, new Vector3f(), GameRenderer::getPositionTexColorShader);
    }

    public ResourceLocation getTexturePath() {
        return texturePath;
    }

    public SkyboxTranslation getTranslation() {
        return translation;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();
        TextureTarget skyboxTarget = renderer.getWriteBuffer();

        skyboxTarget.bindWrite(true);
        poseStack.pushPose();
        float ticks = (renderTick + partialTick);

        if (skyboxRotationSpeed.lengthSquared() > 0) {
            Quaternionf rotation = new Quaternionf();
            rotation.mul(Axis.XP.rotationDegrees(ticks * skyboxRotationSpeed.x));
            rotation.mul(Axis.YN.rotationDegrees(ticks * skyboxRotationSpeed.y));
            rotation.mul(Axis.ZP.rotationDegrees(ticks * skyboxRotationSpeed.z));

            poseStack.mulPose(rotation);
        }

        RenderSystem.setShader(skyboxShader);
        SkyBoxRenderer.renderBlockSkybox(poseStack, texturePath,
                skyboxShader.get(),
                translation
        );

        poseStack.popPose();

        renderer.getRenderBuffer().bindWrite(true);

        for (SkyblockPass pass : SkyblockPass.values()) {
            ShaderInstance shader = pass.getShader();
            shader.setSampler("Skybox", skyboxTarget.getColorTextureId());

            RenderCube.renderSkyBlocks(batchData.get(pass), shader);
        }

        batchData.clear();
    }
}
