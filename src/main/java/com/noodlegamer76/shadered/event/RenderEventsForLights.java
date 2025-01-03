package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.lighting.Light;
import com.noodlegamer76.shadered.client.util.lighting.PointLight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
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

            lights.add(new PointLight()
                            .setConstant(1)
                            .setColor(new Vector3f(0.9f, 0.95f, 1.0f))
                            .setPosition(Minecraft.getInstance().player.position().toVector3f()));

            //Render Point Lights
            for (Light light: lights) {
                light.render(event.getPoseStack(), RegisterShadersEvent.pointLight);
            }
            lights.clear();
        }
    }
}
