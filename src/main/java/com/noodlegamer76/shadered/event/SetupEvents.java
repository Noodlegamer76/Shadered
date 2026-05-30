package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.assimp.load.AssimpLoader;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = ShaderedMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupEvents {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        SkyblockRenderer.setup();
        event.enqueueWork(() -> RenderSystem.recordRenderCall(AssimpLoader::load));
    }
}
