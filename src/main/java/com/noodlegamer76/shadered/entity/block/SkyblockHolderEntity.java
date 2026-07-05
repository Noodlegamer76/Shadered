package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.SkyblockFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SkyblockHolderEntity extends BlockEntity {
    private SkyblockPass pass = SkyblockPass.NORMAL;
    private SkyblockType blockType = SkyblockType.STORMY;

    public SkyblockHolderEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("blockType")) {
            String type = tag.getString("blockType");
            if (!type.isEmpty()) {
                blockType = SkyblockType.valueOf(type);
            }
        }
        if (tag.contains("pass")) {
            String pass = tag.getString("pass");
            if (!pass.isEmpty()) {
                this.pass = SkyblockPass.valueOf(pass);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (pass != null) {
            tag.putString("pass", pass.name());
        }
        if (blockType != null) {
            tag.putString("blockType", blockType.name());
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public SkyblockPass getPass() {
        return pass;
    }

    public SkyblockType getBlockType() {
        return blockType;
    }

    public void setBlockType(SkyblockType blockType) {
        this.blockType = blockType;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setPass(SkyblockPass pass) {
        this.pass = pass;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void onPlaced(LivingEntity placer, ItemStack otherHand) {
        if (otherHand.getItem() instanceof SkyblockFilter filter) {
            setPass(filter.getPass());
        }
    }
}
