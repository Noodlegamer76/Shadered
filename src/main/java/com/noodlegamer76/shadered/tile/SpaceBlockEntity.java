package com.noodlegamer76.shadered.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SpaceBlockEntity extends BlockEntity {
    public SpaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.SPACE_BLOCK.get(), pos, blockState);
    }
}
