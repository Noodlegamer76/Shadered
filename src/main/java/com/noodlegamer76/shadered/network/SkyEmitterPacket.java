package com.noodlegamer76.shadered.network;

import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.block.SkyEmitterBlock;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.NetworkEvent;

public class SkyEmitterPacket {
    private final BlockPos pos;
    private final float minDistance;
    private final float maxDistance;
    private final float maxAlpha;

    public SkyEmitterPacket(BlockPos pos, float minDistance, float maxDistance, float maxAlpha) {
        this.pos = pos;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.maxAlpha = maxAlpha;
    }

    public SkyEmitterPacket(FriendlyByteBuf pBuffer) {
        this.minDistance = pBuffer.readFloat();
        this.maxDistance = pBuffer.readFloat();
        this.maxAlpha = pBuffer.readFloat();
        this.pos = pBuffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(minDistance);
        pBuffer.writeFloat(maxDistance);
        pBuffer.writeFloat(maxAlpha);
        pBuffer.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        Player player = context.getSender();
        if (player == null) return;
        Level level = player.level();
        if (!isValid(level, player)) return;

        if (!(level.getBlockEntity(pos) instanceof SkyEmitterEntity skyEmitter)) return;

        skyEmitter.setAlpha(maxAlpha);
        skyEmitter.setMaximumRange(maxDistance);
        skyEmitter.setMinimumRange(minDistance);
    }

    protected boolean isValid(Level level, Player pPlayer) {
        return level.getBlockState(pos).is(InitBlocks.SKY_EMITTER.get()) &&
                pPlayer.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }

    public float getMaxAlpha() {
        return maxAlpha;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public BlockPos getPos() {
        return pos;
    }
}