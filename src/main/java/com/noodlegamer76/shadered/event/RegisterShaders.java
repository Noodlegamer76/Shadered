package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

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

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_warp"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyboxWarp = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "compressor"),
                        DefaultVertexFormat.POSITION),
                (e) -> compressor = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyblock = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_invert"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyblockInvert = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_posterize"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyblockPosterize = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_chromatic_aberration"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyblockChromaticAberration = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_grayscale"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyblockGrayscale = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_screen"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyblockScreen = e);
    }
}
