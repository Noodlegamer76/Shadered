package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.client.RenderTargets;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.SkyBoxRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = Shadered.MODID, value = Dist.CLIENT)
public class RenderLevel {
    public static final ResourceLocation NEBULA = ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "textures/environment/nebula");
    public static final ResourceLocation STORMY = ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "textures/environment/stormy");
    public static final ResourceLocation OCEAN = ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "textures/environment/ocean");
    public static int width, height, prevWidth, prevHeight;

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            RenderTargets.SPACE.clear(false);
            RenderTargets.OCEAN.clear(false);
            RenderTargets.STORMY.clear(false);
            RenderTargets.END_SKY.clear(false);

            if (prevWidth != width || prevHeight != height) {
                prevWidth = width;
                prevHeight = height;

                RenderTargets.SPACE.resize(width, height, false);
                RenderTargets.OCEAN.resize(width, height, false);
                RenderTargets.STORMY.resize(width, height, false);
                RenderTargets.END_SKY.resize(width, height, false);
            }

            RenderTargets.SPACE.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), NEBULA);
            RenderTargets.SPACE.unbindWrite();

            RenderTargets.OCEAN.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), OCEAN);
            RenderTargets.OCEAN.unbindWrite();

            RenderTargets.STORMY.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), STORMY);
            RenderTargets.STORMY.unbindWrite();

            RenderTargets.END_SKY.bindWrite(true);
            SkyBoxRenderer.renderEndSky(event.getPoseStack());
            RenderTargets.END_SKY.unbindWrite();

            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

            RegisterShaders.spaceSkybox.setSampler("Skybox", RenderTargets.SPACE.getColorTextureId());
            RegisterShaders.oceanSkybox.setSampler("Skybox", RenderTargets.OCEAN.getColorTextureId());
            RegisterShaders.stormySkybox.setSampler("Skybox", RenderTargets.STORMY.getColorTextureId());
            RegisterShaders.endSkybox.setSampler("Skybox", RenderTargets.END_SKY.getColorTextureId());
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            RenderCube.renderSkyBlocks(RenderTargets.spaceRenderInfos, RegisterShaders.spaceSkybox);
            RenderCube.renderSkyBlocks(RenderTargets.oceanRenderInfos, RegisterShaders.oceanSkybox);
            RenderCube.renderSkyBlocks(RenderTargets.stormyRenderInfos, RegisterShaders.stormySkybox);
            RenderCube.renderSkyBlocks(RenderTargets.endSkyRenderInfos, RegisterShaders.endSkybox);
            RenderCube.renderCubeWithRenderType(RenderTargets.endRenderInfos, RenderType.endPortal());
        }
    }
}
