package com.noodlegamer76.shadered.client;

import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import com.noodlegamer76.shadered.gui.LightEmitterScreen;
import com.noodlegamer76.shadered.gui.SkyEmitterScreen;
import net.minecraft.client.Minecraft;

public final class ClientHooks {
    private ClientHooks() {
    }

    public static void openSkyEmitterScreen(SkyEmitterEntity skyEmitter) {
        Minecraft.getInstance().setScreen(new SkyEmitterScreen(skyEmitter));
    }

    public static void openLightEmitterScreen(LightBulbEntity lightBulb) {
        Minecraft.getInstance().setScreen(new LightEmitterScreen(lightBulb));
    }
}