package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MimicBlockEntity extends SkyblockEntity {
    public MimicBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.MIMIC_BLOCK.get(), pPos, pBlockState);
    }
}
