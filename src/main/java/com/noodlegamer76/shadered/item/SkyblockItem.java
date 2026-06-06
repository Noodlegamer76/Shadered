package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class SkyblockItem extends BlockItem {
    private final SkyblockType type;

    public SkyblockItem(Block block, Properties properties, SkyblockType type) {
        super(block, properties);
        this.type = type;
    }

    public SkyblockType getType() {
        return type;
    }
}
