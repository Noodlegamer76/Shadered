package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class OceanBlockEntity extends SkyblockEntity {
    public OceanBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.OCEAN_BLOCK.get(), pPos, pBlockState);
    }
}
