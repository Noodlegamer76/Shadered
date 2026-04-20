package com.noodlegamer76.shadered.network;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LightEmitterPacket {
    private final BlockPos pos;
    private final float red;
    private final float green;
    private final float blue;
    private final float radius;

    public LightEmitterPacket(BlockPos pos, float red, float green, float blue, float radius) {
        this.pos = pos;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.radius = radius;
    }

    public LightEmitterPacket(FriendlyByteBuf pBuffer) {
        this.red = pBuffer.readFloat();
        this.green = pBuffer.readFloat();
        this.blue = pBuffer.readFloat();
        this.radius = pBuffer.readFloat();
        this.pos = pBuffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(red);
        pBuffer.writeFloat(green);
        pBuffer.writeFloat(blue);
        pBuffer.writeFloat(radius);
        pBuffer.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        Player player = context.getSender();
        if (player == null) return;
        Level level = player.level();
        if (!isValid(level, player)) return;

        if (!(level.getBlockEntity(pos) instanceof LightBulbEntity lightBulb)) return;

        lightBulb.setRadius(radius);
        lightBulb.setColor(red, green, blue);
    }

    protected boolean isValid(Level level, Player pPlayer) {
        return level.getBlockState(pos).is(InitBlocks.LIGHT_BULB.get()) &&
                pPlayer.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }

    public float getRadius() {
        return radius;
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

    public BlockPos getPos() {
        return pos;
    }
}