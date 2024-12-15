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
            () -> new SpaceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK)));

    public static final RegistryObject<Block> STORMY_BLOCK = BLOCKS.register("stormy_block",
            () -> new StormyBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE)));

    public static final RegistryObject<Block> OCEAN_BLOCK = BLOCKS.register("ocean_block",
            () -> new OceanBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLUE)));

    public static final RegistryObject<Block> DARKNESS_BLOCK = BLOCKS.register("darkness_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK)));

    public static final RegistryObject<Block> LIGHT_BLOCK = BLOCKS.register("light_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.WHITE).emissiveRendering((state, getter, pos) -> true)));

    public static final RegistryObject<Block> END_BLOCK = BLOCKS.register("end_block",
            () -> new EndBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK)));

    public static final RegistryObject<Block> END_SKY_BLOCK = BLOCKS.register("end_sky_block",
            () -> new EndSkyBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.PURPLE)));
}
