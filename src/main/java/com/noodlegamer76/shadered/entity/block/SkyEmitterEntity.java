package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SkyEmitterEntity extends SkyblockHolderEntity {
    private float minimumRange = 5.0f;
    private float maximumRange = 10.0f;
    private float alpha = 1.0f;
    public static final AABB RENDER_BOUNDING_BOX = new AABB(
            -512, -512, -512,
            512, 512, 512
    );

    public SkyEmitterEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SKY_EMITTER.get(), pPos, pBlockState);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1024);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.minimumRange = tag.getFloat("minimumRange");
        this.maximumRange = tag.getFloat("maximumRange");
        this.alpha = tag.getFloat("alpha");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("minimumRange", minimumRange);
        tag.putFloat("maximumRange", maximumRange);
        tag.putFloat("alpha", alpha);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setMaximumRange(float maximumRange) {
        this.maximumRange = maximumRange;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public float getMaximumRange() {
        return maximumRange;
    }

    public void setMinimumRange(float minimumRange) {
        this.minimumRange = minimumRange;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public float getMinimumRange() {
        return minimumRange;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public float getAlpha() {
        return alpha;
    }
}