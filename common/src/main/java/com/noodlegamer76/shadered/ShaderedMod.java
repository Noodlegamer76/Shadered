package com.noodlegamer76.shadered;

import com.google.common.base.Suppliers;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.tile.InitBlockEntities;
import dev.architectury.registry.registries.RegistrarManager;

import java.util.function.Supplier;

public final class ShaderedMod {
    public static final String MOD_ID = "shadered";

    public static void init() {
        InitItems.ITEMS.register();
        InitBlocks.BLOCKS.register();
        InitBlockEntities.BLOCK_ENTITIES.register();
    }
}
