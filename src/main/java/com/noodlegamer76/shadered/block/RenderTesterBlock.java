package com.noodlegamer76.shadered.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RenderTesterBlock extends Block {
    public RenderTesterBlock(Properties pProperties) {
        super(pProperties);
    }

    //@Nullable
    //@Override
    //public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    //    return new RenderTester(pPos, pState);
    //}
//
    //@Override
    //public RenderShape getRenderShape(BlockState pState) {
    //    return RenderShape.ENTITYBLOCK_ANIMATED;
    //}
}
