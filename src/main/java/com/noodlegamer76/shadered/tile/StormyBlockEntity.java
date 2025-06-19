package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StormyBlockEntity extends BlockEntity {
    public StormyBlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.STORMY_BLOCK.get(), pos, blockState);
    }
}
