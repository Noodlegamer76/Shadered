package com.noodlegamer76.shadered.network.GameObjectPayload;

import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ComponentHandler {

    public static void handle(ComponentPayload payload, IPayloadContext context) {
        Level level = context.player().level();

        byte[] bytes = payload.components();
        int id = payload.id();

        GameObject gameObject = (GameObject) level.getEntity(id);

        if (gameObject == null) {
            System.out.println("GameObject with id " + id + " not found while getting components");
            return;
        }

        gameObject.getComponentManager().loadComponents(gameObject, bytes);
    }
}
