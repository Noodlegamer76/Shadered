package com.noodlegamer76.shadered.core.network;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.UUID;

public class GameObjectSerializers {
    public static final GameObjectSerializer<String> STRING = GameObjectSerializer.create(FriendlyByteBuf::writeUtf, FriendlyByteBuf::readUtf);
    public static final GameObjectSerializer<Integer> INTEGER = GameObjectSerializer.create(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt);
    public static final GameObjectSerializer<Integer> VAR_INT = GameObjectSerializer.create(FriendlyByteBuf::writeVarInt, FriendlyByteBuf::readVarInt);
    public static final GameObjectSerializer<Float> FLOAT = GameObjectSerializer.create(FriendlyByteBuf::writeFloat, FriendlyByteBuf::readFloat);
    public static final GameObjectSerializer<Boolean> BOOLEAN = GameObjectSerializer.create(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean);
    public static final GameObjectSerializer<Double> DOUBLE = GameObjectSerializer.create(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble);
    public static final GameObjectSerializer<Long> LONG = GameObjectSerializer.create(FriendlyByteBuf::writeLong, FriendlyByteBuf::readLong);
    public static final GameObjectSerializer<ResourceLocation> RESOURCE_LOCATION = GameObjectSerializer.create(FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::readResourceLocation);
    public static final GameObjectSerializer<Vector3f> VECTOR3F = GameObjectSerializer.create((writer, val) -> writer.writeVector3f(val), (reader) -> reader.readVector3f());
    public static final GameObjectSerializer<Quaternionf> QUATERNION = GameObjectSerializer.create((writer, val) -> writer.writeQuaternion(val), (reader) -> reader.readQuaternion());
    public static final GameObjectSerializer<UUID> UUID = GameObjectSerializer.create((writer, val) -> writer.writeUUID(val), (reader) -> reader.readUUID());
    public static final GameObjectSerializer<ModelResourceLocation> MODEL_RESOURCE_LOCATION = GameObjectSerializer.create(
            (buf, value) -> buf.writeUtf(value.toString()),
            (buf) -> parseModelResourceLocation(buf.readUtf()));

    public static ModelResourceLocation parseModelResourceLocation(String pLocation) {
        if (pLocation == null) {
            throw new IllegalArgumentException("Model location cannot be null");
        }
        int variantSeparatorIndex = pLocation.indexOf('#');

        String resourceLocationPart;
        String variantPart;

        if (variantSeparatorIndex == -1) {
            resourceLocationPart = pLocation;
            variantPart = "";
        } else {
            resourceLocationPart = pLocation.substring(0, variantSeparatorIndex);
            variantPart = pLocation.substring(variantSeparatorIndex + 1);
        }

        ResourceLocation baseLocation = ResourceLocation.tryParse(resourceLocationPart);

        if (baseLocation == null) {
            throw new IllegalArgumentException("Could not parse ResourceLocation part of model location: " + resourceLocationPart);
        }

        return new ModelResourceLocation(baseLocation, variantPart);
    }

}
