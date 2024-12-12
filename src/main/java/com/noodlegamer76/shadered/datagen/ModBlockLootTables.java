package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    @Override
    protected void generate() {
        dropSelf(InitBlocks.RENDER_TESTER_BLOCK.get());
        dropSelf(InitBlocks.SPACE_BLOCK.get());
        dropSelf(InitBlocks.STORMY_BLOCK.get());
        dropSelf(InitBlocks.OCEAN_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return InitBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}