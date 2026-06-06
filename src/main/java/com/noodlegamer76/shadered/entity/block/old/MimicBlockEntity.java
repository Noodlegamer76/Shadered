package com.noodlegamer76.shadered.entity.block.old;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MimicBlockEntity extends OldSkyblockEntity {
    public MimicBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.MIMIC_BLOCK.get(), pPos, pBlockState);
    }
}
