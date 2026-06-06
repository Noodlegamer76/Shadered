package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class IllusoriteOreBlockEntity extends SkyblockHolderEntity {

    public IllusoriteOreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.ILLUSORITE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
}
