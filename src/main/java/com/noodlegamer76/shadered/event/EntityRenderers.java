package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.client.renderer.entity.GameObjectRenderer;
import com.noodlegamer76.shadered.entity.InitEntities;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.noodlegamer76.shadered.ShaderedMod.MODID;


@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class EntityRenderers {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(InitBlockEntities.RENDER_TESTER.get(), TestRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SKY_EMITTER.get(), SkyEmitterRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.MAXWELL.get(), MaxwellRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.ILLUSORITE_BLOCK_ENTITY.get(), IllusoriteOreRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SKYBLOCK.get(), SkyblockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.FILTER_BLOCK_ENTITY.get(), FilterBlockRenderer::new);

        event.registerEntityRenderer(InitEntities.GAME_OBJECT.get(), GameObjectRenderer::new);
    }
}
