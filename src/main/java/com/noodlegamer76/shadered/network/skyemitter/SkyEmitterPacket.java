package com.noodlegamer76.shadered.network.skyemitter;

import com.noodlegamer76.shadered.ShaderedMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public record SkyEmitterPacket(BlockPos pos, float minDistance, float maxDistance, float maxAlpha) implements CustomPacketPayload {

    public static final Type<SkyEmitterPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "sky_emitter"));

    public static final StreamCodec<ByteBuf, BlockPos> BLOCK_POS = new StreamCodec<ByteBuf, BlockPos>() {
        public BlockPos decode(ByteBuf buf) {
            return FriendlyByteBuf.readBlockPos(buf);
        }

        public void encode(ByteBuf buf, BlockPos pos) {
            FriendlyByteBuf.writeBlockPos(buf, pos);
        }
    };

    public static final StreamCodec<ByteBuf, SkyEmitterPacket> STREAM_CODEC = StreamCodec.composite(
            BLOCK_POS,
            SkyEmitterPacket::pos,
            ByteBufCodecs.FLOAT,
            SkyEmitterPacket::minDistance,
            ByteBufCodecs.FLOAT,
            SkyEmitterPacket::maxDistance,
            ByteBufCodecs.FLOAT,
            SkyEmitterPacket::maxAlpha,
            SkyEmitterPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
