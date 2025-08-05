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
public class RegisterShadersEvent {
    public static ShaderInstance spaceShader;
    public static ShaderInstance oceanShader;
    public static ShaderInstance stormyShader;
    public static ShaderInstance endSkyShader;
    public static ShaderInstance depthDiscarder;
    public static ShaderInstance depthMerge;

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {


        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> spaceShader = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> stormyShader = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> endSkyShader = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> oceanShader = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "depth_discarder"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> depthDiscarder = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "depth_merge"),
                        DefaultVertexFormat.POSITION_TEX),
                (e) -> depthMerge = e);
    }
}
