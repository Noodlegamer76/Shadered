package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.registry.SkyBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SkyBlockBlock extends Block implements EntityBlock {
    private final SkyBlockRegistry registry;

    public SkyBlockBlock(Properties properties, SkyBlockRegistry registry) {
        super(properties);
        this.registry = registry;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ;
    }
}
