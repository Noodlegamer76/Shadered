package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SkyBlock extends BlockEntity {

    public SkyBlock(BlockPos blockPos, BlockState blockState) {
        super(InitBlockEntities.SKY_BLOCK.get(), blockPos, blockState);
    }
}
