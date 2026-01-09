package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.Shadered;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture,
                               CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture, Shadered.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {


        //    this.tag(ModTags.Items.RAINBOW_WOOD)
        //            .add(ItemInit.RAINBOW_LOG.get())
//
        //    this.tag(ItemTags.DOORS)
        //            .add(ItemInit.RAINBOW_DOOR.get());
    }
}
