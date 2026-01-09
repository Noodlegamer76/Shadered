package com.noodlegamer76.shadered.creativetabs;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.item.InitItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Shadered.MODID);

    public static RegistryObject<CreativeModeTab> shaderedTab = CREATIVE_TABS.register("shadered_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("shadered.creative_tab"))
            .icon(() -> new ItemStack(InitItems.SPACE_BLOCK.get()))
            .displayItems((displayContext, items) -> {
                items.accept(InitItems.SPACE_BLOCK.get());
                items.accept(InitItems.OCEAN_BLOCK.get());
                items.accept(InitItems.STORMY_BLOCK.get());
                items.accept(InitItems.LIGHT_BLOCK.get());
                items.accept(InitItems.DARKNESS_BLOCK.get());
                items.accept(InitItems.END_BLOCK.get());
                items.accept(InitItems.END_SKY_BLOCK.get());
                items.accept(InitItems.ECLIPSE_BLOCK.get());
            })
            .build());
}
