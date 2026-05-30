package com.noodlegamer76.shadered.entity.block.skyblock;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EclipseBlockEntity extends SkyblockEntity {
    public EclipseBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.ECLIPSE_BLOCK.get(), pPos, pBlockState);
    }
}
