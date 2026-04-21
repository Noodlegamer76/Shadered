package com.noodlegamer76.shadered.creativetabs;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.item.InitItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShaderedTab {

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.shaderedTab.getKey()) {
            event.accept(InitItems.SPACE_BLOCK);
            event.accept(InitItems.OCEAN_BLOCK);
            event.accept(InitItems.STORMY_BLOCK);
            event.accept(InitItems.LIGHT_BLOCK);
            event.accept(InitItems.DARKNESS_BLOCK);
            event.accept(InitItems.END_BLOCK);
            event.accept(InitItems.END_SKY_BLOCK);
            event.accept(InitItems.IRIDIA_BLOCK);
            event.accept(InitItems.ECLIPSE_BLOCK);
            event.accept(InitItems.FOREST_BLOCK);
            event.accept(InitItems.MIMIC_BLOCK);
            event.accept(InitItems.SPACE_COMPRESSOR);
            event.accept(InitItems.CONFIGURATOR);
            event.accept(InitItems.SKYBLOCK_FILTER_NORMAL);
            event.accept(InitItems.SKYBLOCK_FILTER_INVERTED);
            event.accept(InitItems.SKYBLOCK_FILTER_GRAYSCALE);
            event.accept(InitItems.SKYBLOCK_FILTER_POSTERIZE);
            event.accept(InitItems.SKYBLOCK_FILTER_CHROMATIC_ABERRATION);
            event.accept(InitItems.SKYBLOCK_FILTER_SCREEN);
            event.accept(InitItems.SKYBLOCK_FILTER_BLUEPRINT);
            event.accept(InitItems.SKYBLOCK_FILTER_GAMEBOY);
            event.accept(InitItems.SKY_EMITTER);
            event.accept(InitItems.LIGHT_BULB);
            event.accept(InitItems.MAXWELL);
            event.accept(InitItems.WINDOW);
        }
    }
}
