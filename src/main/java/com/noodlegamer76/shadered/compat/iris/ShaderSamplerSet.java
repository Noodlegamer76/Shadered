package com.noodlegamer76.shadered.compat.iris;

import com.google.common.collect.ImmutableSet;
import com.ibm.icu.impl.locale.XCldrStub;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import com.noodlegamer76.shadered.event.RenderEventsForRenderTargets;
import com.noodlegamer76.shadered.mixin.IrisPipelineAccessor;
import com.noodlegamer76.shadered.mixin.IrisPipelineFields;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.gl.image.GlImage;
import net.irisshaders.iris.gl.program.ProgramBuilder;
import net.irisshaders.iris.gl.program.ProgramImages;
import net.irisshaders.iris.gl.program.ProgramSamplers;
import net.irisshaders.iris.gl.texture.*;
import net.irisshaders.iris.layer.BlockEntityRenderStateShard;
import net.irisshaders.iris.pipeline.CustomTextureManager;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.pipeline.programs.ExtendedShader;
import net.irisshaders.iris.pipeline.programs.ShaderKey;
import net.irisshaders.iris.samplers.IrisImages;
import net.irisshaders.iris.samplers.IrisSamplers;
import net.irisshaders.iris.shaderpack.ShaderPack;
import net.irisshaders.iris.shaderpack.loading.ProgramId;
import net.irisshaders.iris.shaderpack.materialmap.BlockEntry;
import net.irisshaders.iris.shaderpack.materialmap.NamespacedId;
import net.irisshaders.iris.shaderpack.texture.TextureFilteringData;
import net.irisshaders.iris.shaderpack.texture.TextureStage;
import net.irisshaders.iris.targets.RenderTargets;
import net.irisshaders.iris.texture.format.TextureFormat;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.List;

import static com.noodlegamer76.shadered.event.RenderEventsForFbos.spaceGlFbo;
import static com.noodlegamer76.shadered.event.RenderEventsForFbos.spaceTexture;

public class ShaderSamplerSet {

    public static void postPipelineCreation(ShaderPack pack) {
        if (Iris.getPipelineManager().getPipeline().isPresent() &&
                Iris.getPipelineManager().getPipeline().get() instanceof IrisRenderingPipeline pipeline) {
            //if (pipeline.getShaderMap().getShader(ShaderKey.BLOCK_ENTITY) instanceof ExtendedShader shader) {
            //    pipeline.addGbufferOrShadowSamplers(ProgramSamplers.builder(shader.getId(), IrisSamplers.WORLD_RESERVED_TEXTURE_UNITS),
            //            ProgramImages.builder(shader.getId()),
            //            () -> ImmutableSet.of(1), false, true, false, false);
//
            //}

            Window window = Minecraft.getInstance().getWindow();

            //CustomTextureManager customTextureManager = ((IrisPipelineAccessor) pipeline).getCustomTextureManager();
            //ProgramSamplers.Builder builder = ProgramSamplers.builder(pipeline.getShaderMap().getShader(ShaderKey.BLOCK_ENTITY).getId(), IrisSamplers.WORLD_RESERVED_TEXTURE_UNITS);
            //ProgramSamplers.CustomTextureSamplerInterceptor customTextureSamplerInterceptor =
            //        ProgramSamplers.customTextureSamplerInterceptor(builder, customTextureManager.getCustomTextureIdMap().getOrDefault(TextureStage.GBUFFERS_AND_SHADOW, Object2ObjectMaps.emptyMap()));

            //ProgramImages.Builder images = ProgramImages.builder(pipeline.getShaderMap().getShader(ShaderKey.BLOCK_ENTITY).getId());
            //IrisSamplers.addCustomTextures(customTextureSamplerInterceptor, Object2ObjectMaps.singleton(
            //        "Sampler0", new GlTexture(TextureType.TEXTURE_2D, window.getWidth(), window.getHeight(), 0,
            //                InternalTextureFormat.RGBA.getGlFormat(), NativeImage.Format.RGBA.glFormat(), PixelType.UNSIGNED_BYTE.getGlFormat(),
            //                ShaderInjector.getTextureData(RenderEventsForFbos.spaceTexture, window.getWidth(), window.getHeight()),
            //                new TextureFilteringData(false, false))));

           //RenderTargets renderTargets = ((IrisPipelineAccessor) pipeline).getRenderTargets();
           //IrisImages.addRenderTargetImages(images, () -> IrisSamplers.WORLD_RESERVED_TEXTURE_UNITS, renderTargets);

            //ProgramSamplers.Builder builder = ProgramSamplers.builder(pipeline.getShaderMap().getShader(ShaderKey.BLOCK_ENTITY).getId(), IrisSamplers.WORLD_RESERVED_TEXTURE_UNITS);
            //IrisSamplers.addCustomTextures(builder, Object2ObjectMaps.singleton("skybox1212", new GlTexture(TextureType.TEXTURE_2D,
            //        window.getWidth(), window.getHeight(), 0, InternalTextureFormat.RGBA8.getGlFormat(), NativeImage.Format.RGBA.glFormat(),
            //        PixelType.UNSIGNED_BYTE.getGlFormat(), ShaderInjector.getTextureData(RenderEventsForRenderTargets.spaceTarget.getColorTextureId(), window.getWidth(), window.getHeight()),
            //        new TextureFilteringData(false, false))));


        }
    }
}
