package com.noodlegamer76.shadered.event;


import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.RenderCube;
import com.noodlegamer76.shadered.client.util.SkyBoxRenderer;
import com.noodlegamer76.shadered.mixin.IrisPipelineAccessor;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.gl.uniform.Uniform;
import net.irisshaders.iris.gl.uniform.UniformUpdateFrequency;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.pipeline.programs.ShaderKey;
import net.irisshaders.iris.shaderpack.materialmap.BlockEntry;
import net.irisshaders.iris.uniforms.custom.CustomUniformFixedInputUniformsHolder;
import net.irisshaders.iris.uniforms.custom.CustomUniforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL44;

import java.util.Collections;

import static com.mojang.blaze3d.platform.GlConst.GL_COLOR_BUFFER_BIT;
import static com.noodlegamer76.shadered.event.RenderEventsForFbos.*;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForRenderTargets {
    private static int width = Minecraft.getInstance().getWindow().getWidth();
    private static int height = Minecraft.getInstance().getWindow().getHeight();
    private static int previousWidth = width;
    private static int previousHeight = height;
    private static boolean init = false;
    public static TextureTarget spaceTarget;
    public static boolean isSkyblock = false;
    public static int skyblockId = Integer.MIN_VALUE;


    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();

            if (!init) {
                spaceTarget = new TextureTarget(width, height, false, false);

                init = true;
            }

            if (previousWidth != width || previousHeight != height) {
                spaceTarget = new TextureTarget(width, height, false, false);
            }
            previousWidth = width;

            previousHeight = height;

            spaceTarget.setClearColor(1.0f, 0.0f, 1.0f, 1.0f);
            spaceTarget.clear(false);
            spaceTarget.bindWrite(true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), NEBULA);
            spaceTarget.unbindWrite();
            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            //spaceTarget.bindRead();
            //RenderCube.renderSkyBlocks(spacePositions, event.getPartialTick(), spacePose, spaceTarget);
            //spaceTarget.unbindRead();
            //Minecraft.getInstance().getMainRenderTarget().bindRead();
        }
    }
}
