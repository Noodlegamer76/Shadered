package com.noodlegamer76.shadered.network.lightemitterpayload;

import com.noodlegamer76.shadered.ShaderedMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record LightEmitterPayload(
        BlockPos pos,
        float red,
        float green,
        float blue,
        float radius
) implements CustomPacketPayload {

    public static final Type<LightEmitterPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "light_emitter"));

    private static final StreamCodec<ByteBuf, BlockPos> BLOCK_POS = new StreamCodec<ByteBuf, BlockPos>() {
        public BlockPos decode(ByteBuf buf) {
            return new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        }

        public void encode(ByteBuf buf, BlockPos pos) {
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
        }
    };

    public static final StreamCodec<ByteBuf, LightEmitterPayload> STREAM_CODEC = StreamCodec.composite(
            BLOCK_POS,
            LightEmitterPayload::pos,
            ByteBufCodecs.FLOAT,
            LightEmitterPayload::red,
            ByteBufCodecs.FLOAT,
            LightEmitterPayload::green,
            ByteBufCodecs.FLOAT,
            LightEmitterPayload::blue,
            ByteBufCodecs.FLOAT,
            LightEmitterPayload::radius,
            LightEmitterPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
