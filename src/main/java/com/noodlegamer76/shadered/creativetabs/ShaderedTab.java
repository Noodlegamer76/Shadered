package com.noodlegamer76.shadered.creativetabs;

import com.noodlegamer76.shadered.item.InitItems;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShaderedTab {
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.shaderedTab.getKey()) {
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
