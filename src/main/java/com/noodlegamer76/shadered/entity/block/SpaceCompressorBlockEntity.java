package com.noodlegamer76.shadered.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SpaceCompressorBlockEntity extends BlockEntity {
    private BlockPos pos1;
    private BlockPos pos2;

    public SpaceCompressorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SPACE_COMPRESSOR.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag area = new CompoundTag();
        if (pos1 != null) {
            area.putInt("x1", pos1.getX());
            area.putInt("y1", pos1.getY());
            area.putInt("z1", pos1.getZ());
        }
        if (pos2 != null) {
            area.putInt("x2", pos2.getX());
            area.putInt("y2", pos2.getY());
            area.putInt("z2", pos2.getZ());
        }
        tag.put("shadered:area", area);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("shadered:area", Tag.TAG_COMPOUND)) {
            CompoundTag area = tag.getCompound("shadered:area");
            if (area.contains("x1")) pos1 = new BlockPos(area.getInt("x1"), area.getInt("y1"), area.getInt("z1"));
            if (area.contains("x2")) pos2 = new BlockPos(area.getInt("x2"), area.getInt("y2"), area.getInt("z2"));
        }
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

    public BlockPos getPos1() {
        return pos1;
    }

    public BlockPos getPos2() {
        return pos2;
    }

    public void setPos1(BlockPos pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(BlockPos pos2) {
        this.pos2 = pos2;
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (pos1 == null || pos2 == null) {
            return super.getRenderBoundingBox();
        } else {
            Vec3i min = new Vec3i(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
            Vec3i max = new Vec3i(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
            return new AABB(new Vec3(min.getX(), min.getY(), min.getZ()), new Vec3(max.getX() + 1, max.getY() + 1, max.getZ() + 1));
        }
    }
}
