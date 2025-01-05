package com.noodlegamer76.shadered.util.register;

import com.noodlegamer76.shadered.block.OceanBlock;
import com.noodlegamer76.shadered.block.StormyBlock;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import static com.noodlegamer76.shadered.block.InitBlocks.BLOCKS;
import static com.noodlegamer76.shadered.item.InitItems.ITEMS;

public class SimpleBlockWithItemRegister {
    public final RegistryObject<Block> BLOCK;
    public final RegistryObject<Item> ITEM;

    public SimpleBlockWithItemRegister(String name, BlockBehaviour.Properties properties) {
        BLOCK = BLOCKS.register(name,
                () -> new Block(properties));
        ITEM = ITEMS.register(name,
                () -> new BlockItem(BLOCK.get(), new Item.Properties()));
    }
}
