package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;

import java.util.Collections;
import java.util.List;

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

            return SkyblockHolderBlockItem.create(type, pass, item);
        }

        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof SkyblockHolderEntity entity) {
                SkyblockType type = entity.getBlockType();
                SkyblockPass pass = entity.getPass();

                Item itemType = SkyblockRegistry.getItem(type).getItem();
                ItemStack stack = SkyblockHolderBlockItem.create(type, pass, itemType);

                if (!player.isCreative()) {
                    popResource(level, pos, stack);
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return Collections.emptyList();
    }
}
