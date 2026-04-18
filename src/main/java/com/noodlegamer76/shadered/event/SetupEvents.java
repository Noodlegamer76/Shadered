package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.assimp.load.AssimpLoader;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.network.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        SkyblockRenderer.setup();
        event.enqueueWork(() -> RenderSystem.recordRenderCall(AssimpLoader::load));
    }

    @SubscribeEvent
    public static void setupCommon(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }
}
