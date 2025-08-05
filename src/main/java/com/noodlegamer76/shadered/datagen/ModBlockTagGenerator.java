package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider  {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(InitBlocks.SPACE_BLOCK.get())
                .add(InitBlocks.STORMY_BLOCK.get())
                .add(InitBlocks.OCEAN_BLOCK.get())
                .add(InitBlocks.DARKNESS_BLOCK.get())
                .add(InitBlocks.LIGHT_BLOCK.get())
                .add(InitBlocks.END_BLOCK.get())
                .add(InitBlocks.END_SKY_BLOCK.get());
    }
}
