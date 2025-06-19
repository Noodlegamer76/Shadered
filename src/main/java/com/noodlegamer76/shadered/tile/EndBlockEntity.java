package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndBlockEntity extends BlockEntity {
    public EndBlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.END_BLOCK.get(), pos, blockState);
    }
}
