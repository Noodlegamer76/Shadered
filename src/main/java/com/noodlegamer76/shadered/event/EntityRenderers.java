package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.client.renderer.entity.GameObjectRenderer;
import com.noodlegamer76.shadered.entity.InitEntities;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.noodlegamer76.shadered.ShaderedMod.MODID;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderers {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(InitBlockEntities.RENDER_TESTER.get(), TestRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SPACE_BLOCK.get(), SpaceBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.STORMY_BLOCK.get(), StormyBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.OCEAN_BLOCK.get(), OceanBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.END_BLOCK.get(), EndBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.END_SKY_BLOCK.get(), EndSkyBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.ECLIPSE_BLOCK.get(), EclipseBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.LIGHT_BLOCK.get(), LightBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.FOREST_BLOCK.get(), ForestBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.MIMIC_BLOCK.get(), MimicBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SKY_EMITTER.get(), SkyEmitterRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.WINDOW.get(), WindowRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SPACE_COMPRESSOR.get(), SpaceCompressorBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.IRIDIA_BLOCK.get(), IridiaBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.MAXWELL.get(), MaxwellRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.LIGHT_BULB.get(), LightBulbRenderer::new);

        event.registerEntityRenderer(InitEntities.GAME_OBJECT.get(), GameObjectRenderer::new);
    }
}
