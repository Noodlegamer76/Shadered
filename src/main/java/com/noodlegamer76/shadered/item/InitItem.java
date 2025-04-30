package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.Shadered;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Shadered.MODID);

    //public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerSimpleItem("test_item", new Item.Properties());

    //public static final DeferredItem<Item> VOID_CRYSTAL = ITEMS.register("void_crystal",
    //  () -> new VoidCrystal(new Item.Properties()));
}
