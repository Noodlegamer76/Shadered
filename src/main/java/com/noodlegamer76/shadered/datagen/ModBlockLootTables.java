package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.item.InitItems;
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
        dropSelf(InitBlocks.DARKNESS_BLOCK.get());
        dropSelf(InitBlocks.LIGHT_BLOCK.get());
        dropSelf(InitBlocks.FOREST_BLOCK.get());
        dropSelf(InitBlocks.MIMIC_BLOCK.get());
        dropSelf(InitBlocks.SKY_EMITTER.get());
        dropSelf(InitBlocks.MAXWELL.get());
        dropSelf(InitBlocks.LIGHT_BULB.get());
        dropSelf(InitBlocks.END_BLOCK.get());
        dropSelf(InitBlocks.IRIDIA_BLOCK.get());
        dropSelf(InitBlocks.END_SKY_BLOCK.get());
        dropSelf(InitBlocks.ECLIPSE_BLOCK.get());
        dropSelf(InitBlocks.SPACE_COMPRESSOR.get());
        dropSelf(InitBlocks.FILTER_BLOCK.get());
        dropSelf(InitBlocks.SKYBLOCK.get());

        this.add(InitBlocks.ILLUSORITE_ORE.get(),
                block -> createOreDrop(
                        InitBlocks.ILLUSORITE_ORE.get(),
                        InitItems.STORMY_BLOCK.get()
                ));
        this.add(InitBlocks.DEEPSLATE_ILLUSORITE_ORE.get(),
                block -> createOreDrop(
                        InitBlocks.DEEPSLATE_ILLUSORITE_ORE.get(),
                        InitItems.STORMY_BLOCK.get()
                ));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return InitBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
