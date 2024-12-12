package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RenderTester extends BlockEntity {
    public RenderTester(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.RENDER_TESTER.get(), pPos, pBlockState);
    }
}
