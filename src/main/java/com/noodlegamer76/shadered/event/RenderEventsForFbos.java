package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Shadered.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {

    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
        SkyblockRenderer.getInstance().renderSkybox(event);
    }
}
