package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SpaceBlockEntity extends BlockEntity {

    public SpaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SPACE_BLOCK.get(), pPos, pBlockState);
    }
}
