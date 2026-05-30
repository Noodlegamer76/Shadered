package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.entity.block.skyblock.IridiaBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class IridiaBlock extends Skyblock implements EntityBlock {
    public IridiaBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IridiaBlockEntity(pPos, pState);
    }
}
