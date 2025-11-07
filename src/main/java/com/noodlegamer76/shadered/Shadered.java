package com.noodlegamer76.shadered;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.creativetabs.InitCreativeTabs;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.tile.InitBlockEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Shadered.MODID)
public class Shadered
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "shadered";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Shadered(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        InitItems.ITEMS.register(modEventBus);
        InitBlocks.BLOCKS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(InitBlockEntities.SPACE_BLOCK.get(), SpaceBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.OCEAN_BLOCK.get(), OceanBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.STORMY_BLOCK.get(), StormyBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.END_BLOCK.get(), EndBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.END_SKY_BLOCK.get(), EndSkyBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.ECLIPSE_BLOCK.get(), EclipseBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.PS1_BLOCK.get(), Ps1BlockRenderer::new);
        }
    }
}
