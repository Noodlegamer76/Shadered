package com.noodlegamer76.shadered.network;

import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ComponentPacket {
    public int id;
    public byte[] components;

    public ComponentPacket(int id, byte[] components) {
        this.id = id;
        this.components = components;
    }

    public ComponentPacket(FriendlyByteBuf buf) {
        id = buf.readVarInt();
        components = buf.readByteArray();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(id);
        buf.writeByteArray(components);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        Level level = Minecraft.getInstance().level;

        if (level == null) return;

        GameObject gameObject = (GameObject) level.getEntity(id);

        if (gameObject == null) {
            System.out.println("GameObject with id " + id + " not found while getting components");
            return;
        }

        gameObject.getComponentManager().loadComponents(gameObject, components);
    }
}