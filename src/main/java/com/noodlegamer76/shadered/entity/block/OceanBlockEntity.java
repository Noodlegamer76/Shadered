package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OceanBlockEntity extends BlockEntity {
    public OceanBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.OCEAN_BLOCK.get(), pPos, pBlockState);
    }
}
