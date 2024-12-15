package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EndSkyBlockEntity extends BlockEntity {
    public EndSkyBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.END_SKY_BLOCK.get(), pPos, pBlockState);
    }
}
