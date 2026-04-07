package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.SkyblockFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SkyblockEntity extends BlockEntity {
    private SkyblockPass pass = SkyblockPass.NORMAL;

    public SkyblockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        int pass = tag.getInt("pass");
        if (pass <= 0 || pass >= SkyblockPass.values().length) {
            pass = 0;
        }
        this.pass = SkyblockPass.values()[pass];
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("pass", pass.ordinal());
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

    public SkyblockPass getPass() {
        return pass;
    }

    public void setPass(SkyblockPass pass) {
        this.pass = pass;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    public void onPlaced(LivingEntity placer, ItemStack otherHand) {
        if (otherHand.getItem() instanceof SkyblockFilter filter) {
            setPass(filter.getPass());
        }
    }
}
