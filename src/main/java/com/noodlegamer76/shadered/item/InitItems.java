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
            () -> new SpaceBlockItem(InitBlocks.SPACE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STORMY_BLOCK = ITEMS.register("stormy_block",
            () -> new StormyBlockItem(InitBlocks.STORMY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OCEAN_BLOCK = ITEMS.register("ocean_block",
            () -> new OceanBlockItem(InitBlocks.OCEAN_BLOCK.get(), new Item.Properties()));
}
