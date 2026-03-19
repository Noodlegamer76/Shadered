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
    public static ShaderInstance depthDiscarder;
    public static ShaderInstance invert;
    public static ShaderInstance compressor;
    public static ShaderInstance skyboxWarp;

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skyblock_warp"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> skyboxWarp = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "depth_discarder"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> depthDiscarder = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "invert"),
                        DefaultVertexFormat.POSITION),
                (e) -> invert = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "compressor"),
                        DefaultVertexFormat.POSITION),
                (e) -> compressor = e);
    }
}
