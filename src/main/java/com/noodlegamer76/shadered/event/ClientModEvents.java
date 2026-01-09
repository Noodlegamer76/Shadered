package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.client.renderer.block.*;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Shadered.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(InitBlockEntities.SPACE_BLOCK.get(), SpaceBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.STORMY_BLOCK.get(), StormyBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.OCEAN_BLOCK.get(), OceanBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.END_BLOCK.get(), EndBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.END_SKY_BLOCK.get(), EndSkyBlockRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntities.ECLIPSE_BLOCK.get(), EclipseBlockEntityRenderer::new);
    }
}