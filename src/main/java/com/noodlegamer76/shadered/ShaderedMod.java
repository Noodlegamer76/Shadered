package com.noodlegamer76.shadered;

import com.mojang.logging.LogUtils;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.core.component.InitComponents;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import com.noodlegamer76.shadered.creativetabs.InitCreativeTabs;
import com.noodlegamer76.shadered.entity.InitEntities;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.worldgen.features.InitFeatures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(ShaderedMod.MODID)
public class ShaderedMod {
    public static final String MODID = "shadered";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ShaderedMod(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.dist.isClient()) {
            NativeLibraryLoader.loadNatives();
        }

        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitEntities.ENTITY_TYPES.register(modEventBus);
        InitComponents.COMPONENT_TYPES.register(modEventBus);
        InitFeatures.FEATURES.register(modEventBus);
        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
        InitDataComponents.COMPONENT_TYPES.register(modEventBus);
    }
}
