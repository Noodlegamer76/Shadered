package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.*;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Quaternionf;


@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {

    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {
        SkyblockRenderer.preRender();

        RenderLevelStageEvent.Stage stage = event.getStage();
        PoseStack poseStack = event.getPoseStack();
        float partialTick = event.getPartialTick();
        int renderTick = event.getRenderTick();

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
    }
}
