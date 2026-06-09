package com.noodlegamer76.shadered.client.renderer.complexpasses;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.util.PassType;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.RenderableComplexPass;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class FilterBlockEncodeComplexPass implements RenderableComplexPass {
    private final SkyblockBatchData batchData;
    private TextureTarget filterTarget;

    public FilterBlockEncodeComplexPass(SkyblockBatchData batchData) {
        this.batchData = batchData;
    }

    @Override
    public PassType getType() {
        return PassType.GEOMETRY;
    }

    @Override
    public void render(RenderStage stage, PoseStack poseStack, int renderTick, float partialTick) {
        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        if (filterTarget == null) {
            filterTarget = new TextureTarget(
                    renderer.getPreviousWidth(),
                    renderer.getPreviousHeight(),
                    true,
                    false
            );
        } else if (filterTarget.width != renderer.getPreviousWidth() || filterTarget.height != renderer.getPreviousHeight()) {
            filterTarget.resize(
                    renderer.getPreviousWidth(),
                    renderer.getPreviousHeight(),
                    false
            );
        } else {
            filterTarget.clear(false);
        }

        TextureTarget writeTarget = renderer.getWriteBuffer();
        TextureTarget renderTarget = renderer.getRenderBuffer();

        filterTarget.bindWrite(true);

        ShaderInstance filterBlockShader = RegisterShaders.getFilterBlock();
        RenderSystem.setShader(() -> filterBlockShader);

        float[] color = RenderSystem.getShaderColor();

        for (SkyblockPass pass : SkyblockPass.values()) {
            float effectValue = (pass.ordinal() + 1) / 255.0f;

            RenderSystem.setShaderColor(effectValue, 1.0f, 1.0f, 1.0f);

            RenderCube.renderSkyBlocks(batchData.get(pass), false, filterBlockShader);
        }

        RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);

        batchData.clear();
        renderTarget.bindWrite(true);
    }

    public TextureTarget getFilterTarget() {
        return filterTarget;
    }
}
