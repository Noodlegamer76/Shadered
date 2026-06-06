package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
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
        event.registerBlockEntityRenderer(InitBlockEntities.SKY_EMITTER.get(), SkyEmitterRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SPACE_COMPRESSOR.get(), SpaceCompressorBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.MAXWELL.get(), MaxwellRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.LIGHT_BULB.get(), LightBulbRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.ILLUSORITE_BLOCK_ENTITY.get(), IllusoriteOreRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.SKYBLOCK.get(), SkyblockEntityRenderer::new);

        event.registerEntityRenderer(InitEntities.GAME_OBJECT.get(), GameObjectRenderer::new);
    }
}
