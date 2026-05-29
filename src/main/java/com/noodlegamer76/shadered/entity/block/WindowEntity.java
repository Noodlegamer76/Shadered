package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WindowEntity extends SkyblockEntity {

    public WindowEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.WINDOW.get(), pPos, pBlockState);
    }
}
