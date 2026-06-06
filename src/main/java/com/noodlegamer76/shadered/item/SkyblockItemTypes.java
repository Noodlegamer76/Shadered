package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public enum SkyblockItemTypes {
    SPACE("item.shadered.space_block", () -> InitItems.SPACE_BLOCK.get()),
    STORMY("item.shadered.stormy_block", () -> InitItems.STORMY_BLOCK.get()),
    OCEAN("item.shadered.ocean_block", () -> InitItems.OCEAN_BLOCK.get()),
    LIGHT("item.shadered.light_block", () -> InitItems.LIGHT_BLOCK.get()),
    END("item.shadered.end_block", () -> InitItems.END_BLOCK.get()),
    IRIDIA("item.shadered.iridia_block", () -> InitItems.IRIDIA_BLOCK.get()),
    END_SKY("item.shadered.end_sky_block", () -> InitItems.END_SKY_BLOCK.get()),
    ECLIPSE("item.shadered.eclipse_block", () -> InitItems.ECLIPSE_BLOCK.get()),
    FOREST("item.shadered.forest_block", () -> InitItems.FOREST_BLOCK.get()),
    MIMIC("item.shadered.mimic_block", () -> InitItems.MIMIC_BLOCK.get());

    private final String translationKey;
    private final Supplier<Item> itemSupplier;

    SkyblockItemTypes(String translationKey, Supplier<Item> itemSupplier) {
        this.translationKey = translationKey;
        this.itemSupplier = itemSupplier;
    }

    public Item getItem() {
        return itemSupplier.get();
    }

    public String getTranslationKey() {
        return translationKey;
    }
}