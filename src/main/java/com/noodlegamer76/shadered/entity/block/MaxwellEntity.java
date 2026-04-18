package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.renderer.assimp.RenderableModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MaxwellEntity extends BlockEntity {
    private RenderableModel model;

    public MaxwellEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.MAXWELL.get(), pPos, pBlockState);
    }

    public RenderableModel getModel() {
        return model;
    }

    public void setModel(RenderableModel model) {
        this.model = model;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox().inflate(1);
    }
}
