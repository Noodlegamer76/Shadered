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
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(0 * 3, -60, 0 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(10 * 3, -60, 0 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(0 * 3, -60, 10 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(20 * 3, -60, 10 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(30 * 3, -60, 10 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(10 * 3, -60, 10 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(20 * 3, -60, 30 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(10 * 3, -60, 30 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(0 * 3, -60, 20 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(0 * 3, -60, 30 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(10 * 3, -60, 20 * 3)));
            lights.add(new Light().setAlpha((float) Math.random() * 100.0f).setColor(new Vector3f((float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10)).setPosition(new Vector3f(20 * 3, -60, 20 * 3)));

            for (Light light: lights) {
                light.render(event.getPoseStack());
            }
            lights.clear();
        }
    }
}
