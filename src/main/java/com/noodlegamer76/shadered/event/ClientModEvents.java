package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.mixin.accessor.ItemBlockRenderTypesAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.NamedRenderTypeManager;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Shadered.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onNamedRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register("eclipse", ModRenderTypes.ECLIPSE, Sheets.chestSheet());
        event.register("space", ModRenderTypes.SPACE, Sheets.chestSheet());
        event.register("ocean", ModRenderTypes.OCEAN, Sheets.chestSheet());
        event.register("stormy", ModRenderTypes.STORMY, Sheets.chestSheet());
        event.register("end", ModRenderTypes.END_BLOCK, Sheets.chestSheet());
        event.register("end_sky", ModRenderTypes.END_SKY, Sheets.chestSheet());
    }
}