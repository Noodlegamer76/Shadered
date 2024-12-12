package com.noodlegamer76.shadered;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.block.StormyBlock;
import com.noodlegamer76.shadered.client.model.BlockModel;
import com.noodlegamer76.shadered.client.renderer.block.OceanBlockRenderer;
import com.noodlegamer76.shadered.client.renderer.block.SpaceBlockRenderer;
import com.noodlegamer76.shadered.client.renderer.block.StormyBlockRenderer;
import com.noodlegamer76.shadered.client.renderer.block.TestRenderer;
import com.noodlegamer76.shadered.creativetabs.InitCreativeTabs;
import com.noodlegamer76.shadered.creativetabs.ShaderedTab;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import com.noodlegamer76.shadered.item.InitItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ShaderedMod.MODID)
public class ShaderedMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "shadered";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ShaderedMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
        modEventBus.register(new ShaderedTab());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(InitBlockEntities.SPACE_BLOCK.get(), SpaceBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.STORMY_BLOCK.get(), StormyBlockRenderer::new);
            event.registerBlockEntityRenderer(InitBlockEntities.OCEAN_BLOCK.get(), OceanBlockRenderer::new);
        }

        @SubscribeEvent
        public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BlockModel.LAYER_LOCATION, BlockModel::createBodyLayer);
        }
    }
}
