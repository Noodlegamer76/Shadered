package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.registry.SkyBlockRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ShaderedMod.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<SkyBlockBlock> SPACE_BLOCK = BLOCKS.register("space_block",
            () -> new SkyBlockBlock(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<SkyBlockBlock> OCEAN_BLOCK = BLOCKS.register("ocean_block",
            () -> new SkyBlockBlock(BlockBehaviour.Properties.of()));
}