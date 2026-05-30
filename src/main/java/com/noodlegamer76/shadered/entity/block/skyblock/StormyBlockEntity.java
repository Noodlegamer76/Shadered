package com.noodlegamer76.shadered.entity.block.skyblock;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StormyBlockEntity extends SkyblockEntity {
    public StormyBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.STORMY_BLOCK.get(), pPos, pBlockState);
    }
}
