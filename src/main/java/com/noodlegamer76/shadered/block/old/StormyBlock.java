package com.noodlegamer76.shadered.block.old;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import com.noodlegamer76.shadered.entity.block.old.OldSkyblockEntity;
import com.noodlegamer76.shadered.entity.block.old.StormyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StormyBlock extends OldSkyblock {
    public StormyBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StormyBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(
                pBlockEntityType,
                InitBlockEntities.STORMY_BLOCK.get(),
                OldSkyblockEntity::tick
        );
    }
}
