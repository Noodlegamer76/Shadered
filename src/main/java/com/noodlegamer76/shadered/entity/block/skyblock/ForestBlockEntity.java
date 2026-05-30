package com.noodlegamer76.shadered.entity.block.skyblock;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ForestBlockEntity extends SkyblockEntity {
    public ForestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.FOREST_BLOCK.get(), pPos, pBlockState);
    }
}
