package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.ShaderedMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ShaderedMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(new Item.Properties()));
}
