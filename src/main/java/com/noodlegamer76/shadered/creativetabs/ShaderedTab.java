package com.noodlegamer76.shadered.creativetabs;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.item.InitItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = ShaderedMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShaderedTab {

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.shaderedTab.getKey()) {
            event.accept(InitItems.SPACE_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.OCEAN_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.STORMY_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.LIGHT_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.DARKNESS_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.END_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.END_SKY_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.IRIDIA_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.ECLIPSE_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.FOREST_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.MIMIC_BLOCK.get().getDefaultInstance());
            event.accept(InitItems.SPACE_COMPRESSOR.get().getDefaultInstance());
            event.accept(InitItems.CONFIGURATOR.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_NORMAL.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_INVERTED.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_GRAYSCALE.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_POSTERIZE.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_CHROMATIC_ABERRATION.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_SCREEN.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_BLUEPRINT.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_FILTER_GAMEBOY.get().getDefaultInstance());
            event.accept(InitItems.SKY_EMITTER.get().getDefaultInstance());
            event.accept(InitItems.SKYBLOCK_PAINTING.get().getDefaultInstance());
            event.accept(InitItems.MAXWELL.get().getDefaultInstance());
            event.accept(InitItems.WINDOW.get().getDefaultInstance());
        }
    }
}
