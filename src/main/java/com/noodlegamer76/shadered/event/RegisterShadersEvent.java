package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.ExtendedShaderInstance;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterShadersEvent {
    public static ShaderInstance TEST;
    public static ShaderInstance skybox;
    public static ShaderInstance pointLight;
    public static ShaderInstance subtractPointLight;
    public static ShaderInstance postDepth;
    public static ShaderInstance spaceShader;
    public static ShaderInstance oceanShader;
    public static ShaderInstance stormyShader;
    public static ShaderInstance endSkyShader;
    public static ShaderInstance areaFog;
    public static ShaderInstance painting1Shader;
    public static ExtendedShaderInstance puddleShader;

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> skybox = e);

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
                        new ResourceLocation(ShaderedMod.MODID, "point_light"),
                        DefaultVertexFormat.POSITION),
                (e) -> pointLight = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "subtract_point_light"),
                        DefaultVertexFormat.POSITION),
                (e) -> subtractPointLight = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "post_depth"),
                        DefaultVertexFormat.POSITION),
                (e) -> postDepth = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> oceanShader = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "area_fog"),
                        DefaultVertexFormat.POSITION),
                (e) -> areaFog = e);

        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> painting1Shader = e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(ShaderedMod.MODID, "puddle"),
                        DefaultVertexFormat.POSITION_COLOR),
                (e) -> puddleShader = (ExtendedShaderInstance) e);
    }
}
