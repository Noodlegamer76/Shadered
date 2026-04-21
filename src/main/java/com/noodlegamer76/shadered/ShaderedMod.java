package com.noodlegamer76.shadered;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.core.component.InitComponents;
import com.noodlegamer76.shadered.creativetabs.InitCreativeTabs;
import com.noodlegamer76.shadered.creativetabs.ShaderedTab;
import com.noodlegamer76.shadered.entity.InitEntities;
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

    public ShaderedMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        NativeLibraryLoader.loadNatives();

        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitEntities.ENTITY_TYPES.register(modEventBus);
        InitComponents.COMPONENT_TYPES.register(modEventBus);

        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
