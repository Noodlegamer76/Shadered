package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.Light;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForLights {
    public static ArrayList<Light> lights = new ArrayList<>();

    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {

            //Render Point Lights
            for (Light light: lights) {
                light.render(event.getPoseStack());
            }
            lights.clear();
        }
    }
}
