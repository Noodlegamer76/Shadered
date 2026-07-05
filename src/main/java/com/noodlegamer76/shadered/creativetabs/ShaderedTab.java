package com.noodlegamer76.shadered.creativetabs;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.item.SkyblockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;

@EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class ShaderedTab {

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.shaderedTab.getKey()) {
            List<Item> skyblocks = List.of(
                    InitItems.SPACE_BLOCK.get(),
                    InitItems.OCEAN_BLOCK.get(),
                    InitItems.STORMY_BLOCK.get(),
                    InitItems.LIGHT_BLOCK.get(),
                    InitItems.END_BLOCK.get(),
                    InitItems.END_SKY_BLOCK.get(),
                    InitItems.IRIDIA_BLOCK.get(),
                    InitItems.ECLIPSE_BLOCK.get(),
                    InitItems.FOREST_BLOCK.get(),
                    InitItems.MIMIC_BLOCK.get()
            );

            for (Item skyblock : skyblocks) {
                if (skyblock instanceof SkyblockItem skyblockItem) {
                    ItemStack stack = SkyblockHolderBlockItem.create(skyblockItem.getType(), SkyblockPass.NORMAL, skyblockItem);
                    event.accept(stack);
                }
            }

            event.accept(InitItems.DARKNESS_BLOCK.get());

            event.accept(SkyblockHolderBlockItem.create(SkyblockType.STORMY, SkyblockPass.INVERTED, InitItems.FILTER_BLOCK.get()));
            event.accept(SkyblockHolderBlockItem.create(SkyblockType.STORMY, SkyblockPass.GRAYSCALE, InitItems.FILTER_BLOCK.get()));
            event.accept(SkyblockHolderBlockItem.create(SkyblockType.STORMY, SkyblockPass.POSTERIZE, InitItems.FILTER_BLOCK.get()));
            event.accept(SkyblockHolderBlockItem.create(SkyblockType.STORMY, SkyblockPass.BLUEPRINT, InitItems.FILTER_BLOCK.get()));
            event.accept(SkyblockHolderBlockItem.create(SkyblockType.STORMY, SkyblockPass.GAMEBOY, InitItems.FILTER_BLOCK.get()));

            event.accept(InitItems.SKYBLOCK_FILTER_NORMAL.get());
            event.accept(InitItems.SKYBLOCK_FILTER_INVERTED.get());
            event.accept(InitItems.SKYBLOCK_FILTER_GRAYSCALE.get());
            event.accept(InitItems.SKYBLOCK_FILTER_POSTERIZE.get());
            event.accept(InitItems.SKYBLOCK_FILTER_CHROMATIC_ABERRATION.get());
            event.accept(InitItems.SKYBLOCK_FILTER_SCREEN.get());
            event.accept(InitItems.SKYBLOCK_FILTER_BLUEPRINT.get());
            event.accept(InitItems.SKYBLOCK_FILTER_GAMEBOY.get());
            event.accept(InitItems.SKY_EMITTER.get());
            event.accept(InitItems.MAXWELL.get());
            event.accept(InitItems.ILLUSORITE.get());

            for (SkyblockType type : SkyblockType.values()) {
                event.accept(SkyblockHolderBlockItem.create(type, SkyblockPass.NORMAL, InitItems.ILLUSORITE_ORE.get()));
            }

            for (SkyblockType type : SkyblockType.values()) {
                event.accept(SkyblockHolderBlockItem.create(type, SkyblockPass.NORMAL, InitItems.DEEPSLATE_ILLUSORITE_ORE.get()));
            }
        }
    }
}
