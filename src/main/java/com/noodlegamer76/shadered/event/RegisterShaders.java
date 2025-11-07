package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.client.ExtendedShaderInstance;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = Shadered.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterShaders {
    public static ExtendedShaderInstance spaceSkybox;
    public static ExtendedShaderInstance oceanSkybox;
    public static ExtendedShaderInstance stormySkybox;
    public static ExtendedShaderInstance endSkybox;
    public static ExtendedShaderInstance eclipseSkybox;
    public static ExtendedShaderInstance ps1Skybox;

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> spaceSkybox = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> oceanSkybox = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> stormySkybox = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> endSkybox = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> eclipseSkybox = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> ps1Skybox = (ExtendedShaderInstance) e);
    }
}
