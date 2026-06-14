package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.PassType;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.RenderableComplexPass;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.joml.Matrix4f;

import java.awt.*;

public class FilterBlockApplyComplexPass implements RenderableComplexPass {
    @Override
    public PassType getType() {
        return PassType.FILTER;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        TextureTarget source = renderer.getCurrentSourceBuffer();
        TextureTarget destination = renderer.getCurrentDestinationBuffer();

        TextureTarget filterTarget =
                SkyblockRenderer.filterBlockComplexPass.getFilterTarget();

        destination.bindWrite(true);

        ShaderInstance filterApplyShader = RegisterShaders.getFilterApply();
        RenderSystem.setShader(() -> filterApplyShader);

        filterApplyShader.setSampler(
                "DiffuseSampler",
                source.getColorTextureId()
        );

        filterApplyShader.setSampler(
                "DepthSampler",
                source.getDepthTextureId()
        );

        filterApplyShader.setSampler(
                "FilterSampler",
                filterTarget.getColorTextureId()
        );

        filterApplyShader.setSampler(
                "FilterDepthSampler",
                filterTarget.getDepthTextureId()
        );

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableBlend();

        Matrix4f orthographic =
                new Matrix4f().ortho(0, 1, 0, 1, -1, 1);

        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(
                orthographic,
                VertexSorting.ORTHOGRAPHIC_Z
        );

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        bufferBuilder.begin(
                VertexFormat.Mode.QUADS,
                DefaultVertexFormat.POSITION_TEX
        );

        bufferBuilder.vertex(-1, -1, 0).uv(0, 0).endVertex();
        bufferBuilder.vertex(1, -1, 0).uv(1, 0).endVertex();
        bufferBuilder.vertex(1, 1, 0).uv(1, 1).endVertex();
        bufferBuilder.vertex(-1, 1, 0).uv(0, 1).endVertex();

        tesselator.end();

        RenderSystem.restoreProjectionMatrix();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
