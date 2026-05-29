package com.noodlegamer76.shadered.network;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPayloadHandler.handlePacket(this));
        });
        context.setPacketHandled(true);
    }

    private static class ClientPayloadHandler {
        private static void handlePacket(ComponentPacket packet) {
            Level level = Minecraft.getInstance().level;
            if (level == null) return;

            GameObject gameObject = (GameObject) level.getEntity(packet.id);
            if (gameObject == null) {
                ShaderedMod.LOGGER.error("Received component packet for non-existent entity {}", packet.id);
                return;
            }

            gameObject.getComponentManager().loadComponents(gameObject, packet.components);
        }
    }
}