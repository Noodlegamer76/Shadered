package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterShaders {
    public static ShaderInstance compressor;
    public static ShaderInstance skyboxWarp;
    public static ShaderInstance skyblock;
    public static ShaderInstance skyblockPosterize;
    public static ShaderInstance skyblockChromaticAberration;
    public static ShaderInstance skyblockGrayscale;
    public static ShaderInstance skyblockInvert;
    public static ShaderInstance skyblockScreen;
    public static ShaderInstance skyblockBlueprint;
    public static ShaderInstance skyblockGameboy;

    private static final Map<String, ShaderInstance> SHADERS = new HashMap<>();

    public static ShaderInstance get(String name) {
        return SHADERS.get(name);
    }

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_warp"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyboxWarp = e;
                    SHADERS.put("skyblock_warp", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "compressor"),
                        DefaultVertexFormat.POSITION),
                (e) -> {
                    compressor = e;
                    SHADERS.put("compressor", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblock = e;
                    SHADERS.put("skyblock", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_invert"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockInvert = e;
                    SHADERS.put("skyblock_invert", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_posterize"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockPosterize = e;
                    SHADERS.put("skyblock_posterize", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_chromatic_aberration"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockChromaticAberration = e;
                    SHADERS.put("skyblock_chromatic_aberration", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_grayscale"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockGrayscale = e;
                    SHADERS.put("skyblock_grayscale", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_screen"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockScreen = e;
                    SHADERS.put("skyblock_screen", e);
                });

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_blueprint"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockBlueprint = e;
                    SHADERS.put("skyblock_blueprint", e);
                });



        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_gameboy"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> {
                    skyblockGameboy = e;
                    SHADERS.put("skyblock_gameboy", e);
                });
    }

    public static ShaderInstance getCompressor() {
        return compressor;
    }

    public static ShaderInstance getSkyboxWarp() {
        return skyboxWarp;
    }

    public static ShaderInstance getSkyblock() {
        return skyblock;
    }

    public static ShaderInstance getSkyblockInvert() {
        return skyblockInvert;
    }

    public static ShaderInstance getSkyblockPosterize() {
        return skyblockPosterize;
    }

    public static ShaderInstance getSkyblockChromaticAberration() {
        return skyblockChromaticAberration;
    }

    public static ShaderInstance getSkyblockGrayscale() {
        return skyblockGrayscale;
    }

    public static ShaderInstance getSkyblockScreen() {
        return skyblockScreen;
    }

    public static ShaderInstance getSkyblockBlueprint() {
        return skyblockBlueprint;
    }

    public static ShaderInstance getSkyblockGameboy() {
        return skyblockGameboy;
    }
}
