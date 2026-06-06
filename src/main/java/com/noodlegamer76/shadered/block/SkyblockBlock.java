package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.item.SkyblockHolderItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class SkyblockBlock extends SkyblockHolderBlock {
    public SkyblockBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof SkyblockHolderEntity entity) {
            SkyblockType type = entity.getBlockType();
            SkyblockPass pass = entity.getPass();

            Item item = SkyblockRegistry.getItem(type).getItem();

            return SkyblockHolderItem.create(type, pass, item);
        }

        return super.getCloneItemStack(state, target, level, pos, player);
    }

}
