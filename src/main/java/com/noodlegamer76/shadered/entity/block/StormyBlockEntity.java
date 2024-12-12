package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StormyBlockEntity extends BlockEntity {
    public StormyBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.STORMY_BLOCK.get(), pPos, pBlockState);
    }
}
