package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Shadered.MODID);

    public static final DeferredItem<Item> SPACE_BLOCK = ITEMS.register("space_block",
      () -> new BlockItem(InitBlocks.SPACE_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> STORMY_BLOCK = ITEMS.register("stormy_block",
            () -> new BlockItem(InitBlocks.STORMY_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> OCEAN_BLOCK = ITEMS.register("ocean_block",
            () -> new BlockItem(InitBlocks.OCEAN_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> DARKNESS_BLOCK = ITEMS.register("darkness_block",
            () -> new BlockItem(InitBlocks.DARKNESS_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_BLOCK = ITEMS.register("light_block",
            () -> new BlockItem(InitBlocks.LIGHT_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> END_BLOCK = ITEMS.register("end_block",
            () -> new BlockItem(InitBlocks.END_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> END_SKY_BLOCK = ITEMS.register("end_sky_block",
            () -> new BlockItem(InitBlocks.END_SKY_BLOCK.get(), new Item.Properties()));
}
