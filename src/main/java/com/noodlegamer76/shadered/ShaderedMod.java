package com.noodlegamer76.shadered;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.creativetabs.InitCreativeTabs;
import com.noodlegamer76.shadered.creativetabs.ShaderedTab;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import com.noodlegamer76.shadered.entity.block.SpaceCompressorBlockEntity;
import com.noodlegamer76.shadered.item.InitItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(ShaderedMod.MODID)
public class ShaderedMod {
    public static final String MODID = "shadered";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ShaderedMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        NativeLibraryLoader.loadNatives();

        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
        modEventBus.register(new ShaderedTab());

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(InitBlockEntities.RENDER_TESTER.get(), TestRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.SPACE_BLOCK.get(), SpaceBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.STORMY_BLOCK.get(), StormyBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.OCEAN_BLOCK.get(), OceanBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.END_BLOCK.get(), EndBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.END_SKY_BLOCK.get(), EndSkyBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.ECLIPSE_BLOCK.get(), EclipseBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.LIGHT_BLOCK.get(), LightBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.FOREST_BLOCK.get(), ForestBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.MIMIC_BLOCK.get(), MimicBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.SKY_EMITTER.get(), SkyEmitterRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.WINDOW.get(), WindowRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.SPACE_COMPRESSOR.get(), SpaceCompressorBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.IRIDIA_BLOCK.get(), IridiaBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.MAXWELL.get(), MaxwellRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.LIGHT_BULB.get(), LightBulbRenderer::new);
        }
    }
}
