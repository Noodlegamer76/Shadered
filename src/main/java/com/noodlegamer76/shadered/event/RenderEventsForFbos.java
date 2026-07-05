package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.RenderStage;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
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
        PoseStack poseStack = event.getPoseStack();
        float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
        int renderTick = event.getRenderTick();

        ComplexPassRenderer renderer = ComplexPassRenderer.getInstance();

        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();

        PoseStack viewMatStack = new PoseStack();
        viewMatStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        viewMatStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));

        if (stage == RenderLevelStageEvent.Stage.AFTER_SKY) {
            renderer.render(RenderStage.AFTER_SKY, viewMatStack, renderTick, partialTick);
        }
        else if (stage == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            renderer.render(RenderStage.AFTER_BLOCK_ENTITIES, poseStack, renderTick, partialTick);
        }
        else if (stage == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            renderer.render(RenderStage.AFTER_LEVEL, poseStack, renderTick, partialTick);
        }
    }
}
