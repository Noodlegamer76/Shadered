package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OceanBlockEntity extends BlockEntity {
    public OceanBlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.OCEAN_BLOCK.get(), pos, blockState);
    }
}
