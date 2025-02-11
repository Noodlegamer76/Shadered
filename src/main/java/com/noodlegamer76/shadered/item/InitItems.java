package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.block.StormyBlock;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ShaderedMod.MODID);

    //dev stuff
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new TestItem(new Item.Properties()));
    public static final RegistryObject<Item> RENDER_TESTER_BLOCK_ITEM = ITEMS.register("render_tester_block",
            () -> new BlockItem(InitBlocks.RENDER_TESTER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPACE_BLOCK = ITEMS.register("space_block",
            () -> new BlockItem(InitBlocks.SPACE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STORMY_BLOCK = ITEMS.register("stormy_block",
            () -> new BlockItem(InitBlocks.STORMY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OCEAN_BLOCK = ITEMS.register("ocean_block",
            () -> new BlockItem(InitBlocks.OCEAN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARKNESS_BLOCK = ITEMS.register("darkness_block",
            () -> new BlockItem(InitBlocks.DARKNESS_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLOCK = ITEMS.register("light_block",
            () -> new BlockItem(InitBlocks.LIGHT_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> END_BLOCK = ITEMS.register("end_block",
            () -> new BlockItem(InitBlocks.END_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> END_SKY_BLOCK = ITEMS.register("end_sky_block",
            () -> new BlockItem(InitBlocks.END_SKY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_SOURCE = ITEMS.register("dark_source",
            () -> new BlockItem(InitBlocks.DARK_SOURCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OIL_DRUM = ITEMS.register("oil_drum",
            () -> new BlockItem(InitBlocks.OIL_DRUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> SKYBLOCK_PAINTING = ITEMS.register("skyblock_painting",
            () -> new SkyblockPaintingItem(InitBlocks.SKYBLOCK_PAINTING.get(), new Item.Properties()));
}
