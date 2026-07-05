package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.old.*;
import com.noodlegamer76.shadered.block.old.LightBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class InitBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ShaderedMod.MODID);

    public static final DeferredHolder<Block, Block> RENDER_TESTER_BLOCK = BLOCKS.register("render_tester",
            () -> new RenderTesterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).noCollission().noOcclusion()));

    public static final DeferredHolder<Block, Block> DARKNESS_BLOCK = BLOCKS.register("darkness_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK)));

    public static final DeferredHolder<Block, Block> SKYBLOCK = BLOCKS.register("skyblock",
            () -> new SkyblockBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK)));

    public static final DeferredHolder<Block, Block> SKY_EMITTER = BLOCKS.register("sky_emitter",
            () -> new SkyEmitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE)));

    public static final DeferredHolder<Block, Block> MAXWELL = BLOCKS.register("maxwell",
            () -> new Maxwell(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion()));

    public static final DeferredHolder<Block, Block> ILLUSORITE_ORE = BLOCKS.register("illusorite_ore",
            () -> new IllusoriteOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .mapColor(DyeColor.LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 7)
            ));

    public static final DeferredHolder<Block, Block> DEEPSLATE_ILLUSORITE_ORE = BLOCKS.register("deepslate_illusorite_ore",
            () -> new IllusoriteOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
                    .mapColor(DyeColor.GRAY)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 7)
            ));

    public static final DeferredHolder<Block, Block> FILTER_BLOCK = BLOCKS.register("filter_block",
            () -> new FilterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion()
            ));

    //DEPRECATED BLOCKS

    public static final DeferredHolder<Block, Block> SPACE_BLOCK = BLOCKS.register("space_block",
            () -> new SpaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> STORMY_BLOCK = BLOCKS.register("stormy_block",
            () -> new StormyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> OCEAN_BLOCK = BLOCKS.register("ocean_block",
            () -> new OceanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLUE).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> LIGHT_BLOCK = BLOCKS.register("light_block",
            () -> new LightBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> END_BLOCK = BLOCKS.register("end_block",
            () -> new EndBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> IRIDIA_BLOCK = BLOCKS.register("iridia_block",
            () -> new IridiaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.PURPLE)));

    public static final DeferredHolder<Block, Block> END_SKY_BLOCK = BLOCKS.register("end_sky_block",
            () -> new EndSkyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.PURPLE).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> ECLIPSE_BLOCK = BLOCKS.register("eclipse_block",
            () -> new EclipseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.RED).lightLevel((state) -> 5)));

    public static final DeferredHolder<Block, Block> FOREST_BLOCK = BLOCKS.register("forest_block",
            () -> new ForestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.GREEN).lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, Block> MIMIC_BLOCK = BLOCKS.register("mimic_block",
            () -> new MimicBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE).lightLevel((state) -> 15)));
}
