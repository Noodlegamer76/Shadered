package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndSkyBlockEntity extends BlockEntity {
    public EndSkyBlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.END_SKY_BLOCK.get(), pos, blockState);
    }
}
