package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LightBulbEntity extends BlockEntity {
    private float red = 1.0f;
    private float green = 1.0f;
    private float blue = 1.0f;
    private float radius = 10.0f;

    public LightBulbEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.LIGHT_BULB.get(), pPos, pBlockState);
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        red = tag.getFloat("red");
        green = tag.getFloat("green");
        blue = tag.getFloat("blue");
        radius = tag.getFloat("radius");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("red", red);
        tag.putFloat("green", green);
        tag.putFloat("blue", blue);
        tag.putFloat("radius", radius);
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

    public void setColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setRadius(float radius) {
        this.radius = radius;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox().inflate(radius);
    }
}
