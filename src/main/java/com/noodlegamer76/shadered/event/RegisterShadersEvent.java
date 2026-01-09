package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.shadered.Shadered;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Shadered.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterShadersEvent {
    public static ShaderInstance skybox;

    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(Shadered.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> skybox = e);
    }
}
