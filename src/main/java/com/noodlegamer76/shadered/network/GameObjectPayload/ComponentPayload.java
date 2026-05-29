package com.noodlegamer76.shadered.network.GameObjectPayload;

import com.noodlegamer76.shadered.ShaderedMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ComponentPayload(int id, byte[] components) implements CustomPacketPayload {

    public static final Type<ComponentPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "components"));

    public static final StreamCodec<ByteBuf, ComponentPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ComponentPayload::id,
            ByteBufCodecs.BYTE_ARRAY,
            ComponentPayload::components,
            ComponentPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
