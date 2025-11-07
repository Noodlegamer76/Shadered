package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EclipseBlockEntity extends BlockEntity {
    public EclipseBlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.ECLIPSE_BLOCK.get(), pos, blockState);
    }
}
