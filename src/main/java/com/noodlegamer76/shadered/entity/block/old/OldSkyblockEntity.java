package com.noodlegamer76.shadered.entity.block.old;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.block.old.*;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.item.SkyblockFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class OldSkyblockEntity extends BlockEntity {
    private SkyblockPass pass = SkyblockPass.NORMAL;
    private SkyblockType cachedType;
    private boolean converting = false;

    public OldSkyblockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OldSkyblockEntity be) {
        if (level.isClientSide) return;

        if (!be.converting) {
            be.converting = true;
            be.cachedType = resolveType(state);

            level.scheduleTick(pos, state.getBlock(), 1);
            return;
        }

        SkyblockType type = be.cachedType;
        SkyblockPass pass = be.pass;

        level.setBlock(pos,
                InitBlocks.SKYBLOCK.get().defaultBlockState(),
                3);

        BlockEntity newBe = level.getBlockEntity(pos);

        if (newBe instanceof SkyblockHolderEntity sky) {
            sky.setBlockType(type);
            sky.setPass(pass);
        }

        be.converting = false;
    }

    private static SkyblockType resolveType(BlockState state) {
        Block block = state.getBlock();

        if (block instanceof SpaceBlock) return SkyblockType.SPACE;
        if (block instanceof EndBlock) return SkyblockType.END;
        if (block instanceof EclipseBlock) return SkyblockType.ECLIPSE;
        if (block instanceof ForestBlock) return SkyblockType.FOREST;
        if (block instanceof StormyBlock) return SkyblockType.STORMY;
        if (block instanceof LightBlock) return SkyblockType.LIGHT;
        if (block instanceof MimicBlock) return SkyblockType.MIMIC;
        if (block instanceof IridiaBlock) return SkyblockType.IRIDIA;
        if (block instanceof OceanBlock) return SkyblockType.OCEAN;
        if (block instanceof EndSkyBlock) return SkyblockType.END_SKY;

        return SkyblockType.SPACE;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        int pass = tag.getInt("pass");
        if (pass <= 0 || pass >= SkyblockPass.values().length) {
            pass = 0;
        }
        this.pass = SkyblockPass.values()[pass];
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putInt("pass", pass.ordinal());
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
