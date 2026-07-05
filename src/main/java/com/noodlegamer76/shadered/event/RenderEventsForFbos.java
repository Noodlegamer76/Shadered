package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.RenderStage;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import net.minecraft.client.Camera;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;


@EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {

    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {
        SkyblockRenderer.preRender();

        RenderLevelStageEvent.Stage stage = event.getStage();
        PoseStack poseStack = new PoseStack();
        float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
        int renderTick = event.getRenderTick();

        poseStack.pushPose();
        poseStack.mulPose(event.getModelViewMatrix());

        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        if (stage == RenderLevelStageEvent.Stage.AFTER_SKY) {
            renderer.render(RenderStage.AFTER_SKY, poseStack, renderTick, partialTick);
            LightUploader.uploadToAll();
        }
        else if (stage == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            renderer.render(RenderStage.AFTER_BLOCK_ENTITIES, poseStack, renderTick, partialTick);
        }
        else if (stage == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            renderer.render(RenderStage.AFTER_LEVEL, poseStack, renderTick, partialTick);
            LightUploader.clearLights();
        }

        poseStack.popPose();
    }
}
