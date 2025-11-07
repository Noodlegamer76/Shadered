package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Ps1BlockEntity extends BlockEntity {
    public Ps1BlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.PS1_BLOCK.get(), pos, blockState);
    }
}
