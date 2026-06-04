package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.client.renderer.block.painting.SkyblockPaintingRenderer;
import com.noodlegamer76.shadered.client.renderer.block.skyblock.*;
import com.noodlegamer76.shadered.client.renderer.entity.GameObjectRenderer;
import com.noodlegamer76.shadered.entity.InitEntities;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.noodlegamer76.shadered.ShaderedMod.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
        event.registerBlockEntityRenderer(InitBlockEntities.SKYBLOCK_PAINTING.get(), SkyblockPaintingRenderer::new);

        event.registerEntityRenderer(InitEntities.GAME_OBJECT.get(), GameObjectRenderer::new);
    }
}
