package com.noodlegamer76.shadered.creativetabs;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.item.InitItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = Shadered.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ShaderedTab {

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.SHADERED_TAB.getKey()) {
            event.accept(InitItems.SPACE_BLOCK);
            event.accept(InitItems.OCEAN_BLOCK);
            event.accept(InitItems.STORMY_BLOCK);
            event.accept(InitItems.DARKNESS_BLOCK);
            event.accept(InitItems.LIGHT_BLOCK);
            event.accept(InitItems.END_BLOCK);
            event.accept(InitItems.END_SKY_BLOCK);
        }
    }
}
