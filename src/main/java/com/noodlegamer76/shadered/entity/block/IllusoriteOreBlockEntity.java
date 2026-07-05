package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class IllusoriteOreBlockEntity extends SkyblockHolderEntity {
    public IllusoriteOreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.ILLUSORITE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
}
