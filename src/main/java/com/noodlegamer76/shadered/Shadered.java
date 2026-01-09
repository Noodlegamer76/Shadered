package com.noodlegamer76.shadered;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.creativetabs.InitCreativeTabs;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import com.noodlegamer76.shadered.item.InitItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Shadered.MODID)
public class Shadered {
    public static final String MODID = "shadered";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Shadered() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
    }
}
