package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.Shadered;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Shadered.MODID);

    public static final DeferredBlock<SpaceBlock> SPACE_BLOCK = BLOCKS.register("space_block",
            () -> new SpaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).lightLevel((state) -> 15)));

    public static final DeferredBlock<StormyBlock> STORMY_BLOCK = BLOCKS.register("stormy_block",
            () -> new StormyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.LIGHT_GRAY).lightLevel((state) -> 15)));

    public static final DeferredBlock<OceanBlock> OCEAN_BLOCK = BLOCKS.register("ocean_block",
            () -> new OceanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLUE).lightLevel((state) -> 15)));

    public static final DeferredBlock<Block> DARKNESS_BLOCK = BLOCKS.register("darkness_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK)));
    public static final DeferredBlock<Block> LIGHT_BLOCK = BLOCKS.register("light_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.WHITE).emissiveRendering((state, getter, pos) -> true)));

    public static final DeferredBlock<EndBlock> END_BLOCK = BLOCKS.register("end_block",
            () -> new EndBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.MAGENTA).lightLevel((state) -> 15)));

    public static final DeferredBlock<EndSkyBlock> END_SKY_BLOCK = BLOCKS.register("end_sky_block",
            () -> new EndSkyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.MAGENTA).lightLevel((state) -> 15)));
}
