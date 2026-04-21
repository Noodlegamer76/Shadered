package com.noodlegamer76.shadered.core.network;

import net.minecraft.network.FriendlyByteBuf;

public interface GameObjectSerializer<T> {
    void serialize(FriendlyByteBuf buffer, T value);
    T deserialize(FriendlyByteBuf buffer);

    @FunctionalInterface
    interface Writer<T> {
        void write(FriendlyByteBuf buf, T value);
    }

    @FunctionalInterface
    interface Reader<T> {
        T read(FriendlyByteBuf buf);
    }

    static <T> GameObjectSerializer<T> create(Writer<T> writer, Reader<T> reader) {
        return new GameObjectSerializer<>() {
            @Override
            public void serialize(FriendlyByteBuf buffer, T value) {
                writer.write(buffer, value);
            }

            @Override
            public T deserialize(FriendlyByteBuf buffer) {
                return reader.read(buffer);
            }
        };
    }
}
