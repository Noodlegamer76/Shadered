package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ShaderedMod.MODID);

    //dev stuff
    public static final DeferredHolder<Item, Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new TestItem(new Item.Properties()));

    public static final DeferredHolder<Item, Item> RENDER_TESTER_BLOCK_ITEM = ITEMS.register("render_tester_block",
            () -> new BlockItem(InitBlocks.RENDER_TESTER_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> DARKNESS_BLOCK = ITEMS.register("darkness_block",
            () -> new BlockItem(InitBlocks.DARKNESS_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> SKY_EMITTER = ITEMS.register("sky_emitter",
            () -> new BlockItem(InitBlocks.SKY_EMITTER.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> MAXWELL = ITEMS.register("maxwell",
            () -> new MaxwellItem(InitBlocks.MAXWELL.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> ILLUSORITE_ORE = ITEMS.register("illusorite_ore",
            () -> new IllusoriteOreItem(InitBlocks.ILLUSORITE_ORE.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> DEEPSLATE_ILLUSORITE_ORE = ITEMS.register("deepslate_illusorite_ore",
            () -> new IllusoriteOreItem(InitBlocks.DEEPSLATE_ILLUSORITE_ORE.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> ILLUSORITE = ITEMS.register("illusorite",
            () -> new Illusorite(new Item.Properties()));

    public static final DeferredHolder<Item, Item> FILTER_BLOCK = ITEMS.register("filter_block",
            () -> new FilterBlockItem(InitBlocks.FILTER_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_NORMAL = ITEMS.register("skyblock_filter_normal",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.NORMAL));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_INVERTED = ITEMS.register("skyblock_filter_inverted",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.INVERTED));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_GRAYSCALE = ITEMS.register("skyblock_filter_grayscale",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.GRAYSCALE));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_POSTERIZE = ITEMS.register("skyblock_filter_posterize",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.POSTERIZE));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_CHROMATIC_ABERRATION = ITEMS.register("skyblock_filter_chromatic_aberration",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.CHROMATIC_ABERRATION));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_SCREEN = ITEMS.register("skyblock_filter_screen",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.SCREEN));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_BLUEPRINT = ITEMS.register("skyblock_filter_blueprint",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.BLUEPRINT));

    public static final DeferredHolder<Item, Item> SKYBLOCK_FILTER_GAMEBOY = ITEMS.register("skyblock_filter_gameboy",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.GAMEBOY));

    public static final DeferredHolder<Item, Item> SPACE_BLOCK = ITEMS.register("space_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.SPACE));

    public static final DeferredHolder<Item, Item> STORMY_BLOCK = ITEMS.register("stormy_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.STORMY));

    public static final DeferredHolder<Item, Item> OCEAN_BLOCK = ITEMS.register("ocean_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.OCEAN));

    public static final DeferredHolder<Item, Item> LIGHT_BLOCK = ITEMS.register("light_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.LIGHT));

    public static final DeferredHolder<Item, Item> END_BLOCK = ITEMS.register("end_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.END));

    public static final DeferredHolder<Item, Item> IRIDIA_BLOCK = ITEMS.register("iridia_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.IRIDIA));

    public static final DeferredHolder<Item, Item> END_SKY_BLOCK = ITEMS.register("end_sky_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.END_SKY));

    public static final DeferredHolder<Item, Item> ECLIPSE_BLOCK = ITEMS.register("eclipse_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.ECLIPSE));

    public static final DeferredHolder<Item, Item> FOREST_BLOCK = ITEMS.register("forest_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.FOREST));

    public static final DeferredHolder<Item, Item> MIMIC_BLOCK = ITEMS.register("mimic_block",
            () -> new SkyblockItem(InitBlocks.SKYBLOCK.get(), new Item.Properties(), SkyblockItemTypes.MIMIC));
}
