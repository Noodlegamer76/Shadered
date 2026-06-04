package com.noodlegamer76.shadered.entity.block.painting;

import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

public class SkyblockPaintingEntity extends ComplexPaintingEntity {
    public SkyblockPaintingEntity(BlockPos pos, BlockState blockState) {
        super(InitBlockEntities.SKYBLOCK_PAINTING.get(), pos, blockState);
    }
}
