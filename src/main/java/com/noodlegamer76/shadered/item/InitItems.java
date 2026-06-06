package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.block.StormyBlock;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
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
            () -> new SkyblockItem(InitBlocks.SPACE_BLOCK.get(), new Item.Properties(), SkyblockType.SPACE));

    public static final RegistryObject<Item> STORMY_BLOCK = ITEMS.register("stormy_block",
            () -> new SkyblockItem(InitBlocks.STORMY_BLOCK.get(), new Item.Properties(), SkyblockType.STORMY));

    public static final RegistryObject<Item> OCEAN_BLOCK = ITEMS.register("ocean_block",
            () -> new SkyblockItem(InitBlocks.OCEAN_BLOCK.get(), new Item.Properties(), SkyblockType.OCEAN));

    public static final RegistryObject<Item> DARKNESS_BLOCK = ITEMS.register("darkness_block",
            () -> new BlockItem(InitBlocks.DARKNESS_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BLOCK = ITEMS.register("light_block",
            () -> new SkyblockItem(InitBlocks.LIGHT_BLOCK.get(), new Item.Properties(), SkyblockType.LIGHT));

    public static final RegistryObject<Item> END_BLOCK = ITEMS.register("end_block",
            () -> new SkyblockItem(InitBlocks.END_BLOCK.get(), new Item.Properties(), SkyblockType.END));

    public static final RegistryObject<Item> IRIDIA_BLOCK = ITEMS.register("iridia_block",
            () -> new SkyblockItem(InitBlocks.IRIDIA_BLOCK.get(), new Item.Properties(), SkyblockType.IRIDIA));

    public static final RegistryObject<Item> END_SKY_BLOCK = ITEMS.register("end_sky_block",
            () -> new SkyblockItem(InitBlocks.END_SKY_BLOCK.get(), new Item.Properties(), null));

    public static final RegistryObject<Item> ECLIPSE_BLOCK = ITEMS.register("eclipse_block",
            () -> new SkyblockItem(InitBlocks.ECLIPSE_BLOCK.get(), new Item.Properties(), SkyblockType.ECLIPSE));

    public static final RegistryObject<Item> FOREST_BLOCK = ITEMS.register("forest_block",
            () -> new SkyblockItem(InitBlocks.FOREST_BLOCK.get(), new Item.Properties(), SkyblockType.FOREST));

    public static final RegistryObject<Item> WINDOW = ITEMS.register("window",
            () -> new BlockItem(InitBlocks.WINDOW.get(), new Item.Properties()));

    public static final RegistryObject<Item> MIMIC_BLOCK = ITEMS.register("mimic_block",
            () -> new SkyblockItem(InitBlocks.MIMIC_BLOCK.get(), new Item.Properties(), SkyblockType.MIMIC));

    public static final RegistryObject<Item> SKY_EMITTER = ITEMS.register("sky_emitter",
            () -> new BlockItem(InitBlocks.SKY_EMITTER.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BULB = ITEMS.register("light_bulb",
            () -> new BlockItem(InitBlocks.LIGHT_BULB.get(), new Item.Properties()));

    public static final RegistryObject<Item> MAXWELL = ITEMS.register("maxwell",
            () -> new MaxwellItem(InitBlocks.MAXWELL.get(), new Item.Properties()));

    public static final RegistryObject<Item> SPACE_COMPRESSOR = ITEMS.register("space_compressor",
            () -> new BlockItem(InitBlocks.SPACE_COMPRESSOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> ILLUSORITE_ORE = ITEMS.register("illusorite_ore",
            () -> new IllusoriteOreBlockItem(InitBlocks.ILLUSORITE_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> DEEPSLATE_ILLUSORITE_ORE = ITEMS.register("deepslate_illusorite_ore",
            () -> new IllusoriteOreBlockItem(InitBlocks.DEEPSLATE_ILLUSORITE_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> CONFIGURATOR = ITEMS.register("configurator",
            () -> new Configurator(new Item.Properties()));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_NORMAL = ITEMS.register("skyblock_filter_normal",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.NORMAL));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_INVERTED = ITEMS.register("skyblock_filter_inverted",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.INVERTED));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_GRAYSCALE = ITEMS.register("skyblock_filter_grayscale",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.GRAYSCALE));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_POSTERIZE = ITEMS.register("skyblock_filter_posterize",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.POSTERIZE));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_CHROMATIC_ABERRATION = ITEMS.register("skyblock_filter_chromatic_aberration",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.CHROMATIC_ABERRATION));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_SCREEN = ITEMS.register("skyblock_filter_screen",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.SCREEN));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_BLUEPRINT = ITEMS.register("skyblock_filter_blueprint",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.BLUEPRINT));

    public static final RegistryObject<Item> SKYBLOCK_FILTER_GAMEBOY = ITEMS.register("skyblock_filter_gameboy",
            () -> new SkyblockFilter(new Item.Properties(), SkyblockPass.GAMEBOY));

}
