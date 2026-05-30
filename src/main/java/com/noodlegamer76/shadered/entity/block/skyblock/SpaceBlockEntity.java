package com.noodlegamer76.shadered.entity.block.skyblock;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SpaceBlockEntity extends SkyblockEntity {
    public SpaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SPACE_BLOCK.get(), pPos, pBlockState);
    }
}
