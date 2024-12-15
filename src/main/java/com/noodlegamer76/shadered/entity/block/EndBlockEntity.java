package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndBlockEntity extends BlockEntity {
    public EndBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.END_BLOCK.get(), pPos, pBlockState);
    }
}
