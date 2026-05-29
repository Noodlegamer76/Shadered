package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class LightBlockEntity extends SkyblockEntity {
    public LightBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.LIGHT_BLOCK.get(), pPos, pBlockState);
    }
}
