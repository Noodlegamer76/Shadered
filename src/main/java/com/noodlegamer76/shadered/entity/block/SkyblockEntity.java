package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SkyblockEntity extends SkyblockHolderEntity {
    public SkyblockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SKYBLOCK.get(), pPos, pBlockState);
    }
}
