package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ShaderedMod.MODID);

    public static final RegistryObject<Block> RENDER_TESTER_BLOCK = BLOCKS.register("render_tester",
            () -> new RenderTesterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noCollission().noOcclusion()));

    public static final RegistryObject<Block> SPACE_BLOCK = BLOCKS.register("space_block",
            () -> new SpaceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> STORMY_BLOCK = BLOCKS.register("stormy_block",
            () -> new StormyBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> OCEAN_BLOCK = BLOCKS.register("ocean_block",
            () -> new OceanBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLUE).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> DARKNESS_BLOCK = BLOCKS.register("darkness_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK)));

    public static final RegistryObject<Block> LIGHT_BLOCK = BLOCKS.register("light_block",
            () -> new LightBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> END_BLOCK = BLOCKS.register("end_block",
            () -> new EndBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> IRIDIA_BLOCK = BLOCKS.register("iridia_block",
            () -> new IridiaBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.PURPLE)));

    public static final RegistryObject<Block> END_SKY_BLOCK = BLOCKS.register("end_sky_block",
            () -> new EndSkyBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.PURPLE).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> ECLIPSE_BLOCK = BLOCKS.register("eclipse_block",
            () -> new EclipseBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.RED).lightLevel((state) -> 5)));

    public static final RegistryObject<Block> FOREST_BLOCK = BLOCKS.register("forest_block",
            () -> new ForestBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.GREEN).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> MIMIC_BLOCK = BLOCKS.register("mimic_block",
            () -> new MimicBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE).lightLevel((state) -> 15)));

    public static final RegistryObject<Block> SKY_EMITTER = BLOCKS.register("sky_emitter",
            () -> new SkyEmitterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE)));

    public static final RegistryObject<Block> MAXWELL = BLOCKS.register("maxwell",
            () -> new Maxwell(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion()));

    public static final RegistryObject<Block> LIGHT_BULB = BLOCKS.register("light_bulb",
            () -> new LightBulb(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE).noOcclusion()));

    public static final RegistryObject<Block> WINDOW = BLOCKS.register("window",
            () -> new WindowBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE)));

    public static final RegistryObject<Block> SPACE_COMPRESSOR = BLOCKS.register("space_compressor",
            () -> new SpaceCompressorBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE)));

    public static final RegistryObject<Block> ILLUSORITE_ORE = BLOCKS.register("illusorite_ore",
            () -> new IllusoriteOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .mapColor(DyeColor.LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 7)
                    .noOcclusion()
            ));

    public static final RegistryObject<Block> DEEPSLATE_ILLUSORITE_ORE = BLOCKS.register("deepslate_illusorite_ore",
            () -> new IllusoriteOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .mapColor(DyeColor.GRAY)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 7)
                    .noOcclusion()
            ));
}
