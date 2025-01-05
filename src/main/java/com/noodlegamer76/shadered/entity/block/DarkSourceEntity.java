package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class DarkSourceEntity extends BlockEntity {
    public DarkSourceEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.DARK_SOURCE.get(), pPos, pBlockState);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(
                getBlockPos().offset(-128, -128, -128),
                getBlockPos().offset(128, 128, 128)
        );
    }
}
