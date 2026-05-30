package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.entity.block.skyblock.SkyblockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WindowEntity extends SkyblockEntity {

    public WindowEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.WINDOW.get(), pPos, pBlockState);
    }
}
