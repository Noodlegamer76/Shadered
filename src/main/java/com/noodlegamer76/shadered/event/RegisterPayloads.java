package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.network.GameObjectPayload.ComponentHandler;
import com.noodlegamer76.shadered.network.GameObjectPayload.ComponentPayload;
import com.noodlegamer76.shadered.network.skyemitter.SkyEmitterHandler;
import com.noodlegamer76.shadered.network.skyemitter.SkyEmitterPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ShaderedMod.MODID)
public class RegisterPayloads {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                ComponentPayload.TYPE,
                ComponentPayload.STREAM_CODEC,
                ComponentHandler::handle
        );

        registrar.playToServer(
                SkyEmitterPacket.TYPE,
                SkyEmitterPacket.STREAM_CODEC,
                SkyEmitterHandler::handle
        );
    }
}
