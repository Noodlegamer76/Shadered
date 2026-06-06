package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.IllusoriteOreBlockEntity;
import com.noodlegamer76.shadered.entity.block.SkyblockEntity;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.item.SkyblockHolderItem;
import com.noodlegamer76.shadered.item.SkyblockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class SkyblockHolderBlock extends Block implements EntityBlock {
    public SkyblockHolderBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return true;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) return;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SkyblockHolderEntity entity)) return;

        if (stack.getItem() instanceof SkyblockHolderItem) {
            CompoundTag tag = stack.getTag();
            if (tag == null || !tag.contains("BlockEntityTag")) return;

            CompoundTag beTag = tag.getCompound("BlockEntityTag");

            if (beTag.contains("blockType")) {
                try {
                    entity.setBlockType(SkyblockType.valueOf(beTag.getString("blockType")));
                } catch (IllegalArgumentException ignored) {
                    entity.setBlockType(SkyblockType.STORMY);
                }
            } else {
                entity.setBlockType(SkyblockType.STORMY);
            }

            if (beTag.contains("pass")) {
                try {
                    entity.setPass(SkyblockPass.valueOf(beTag.getString("pass")));
                } catch (IllegalArgumentException ignored) {
                    entity.setPass(SkyblockPass.NORMAL);
                }
            } else {
                entity.setPass(SkyblockPass.NORMAL);
            }
        }

        if (stack.getItem() instanceof SkyblockItem skyblockItem) {
            SkyblockType type = skyblockItem.getType();
            entity.setBlockType(type);
        }

        ItemStack otherHand = placer != null
                ? placer.getItemInHand(
                placer.getUsedItemHand() == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND
        )
                : ItemStack.EMPTY;

        entity.onPlaced(placer, otherHand);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SkyblockEntity(pPos, pState);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof SkyblockHolderEntity entity) {
            ItemStack stack = new ItemStack(asItem());

            CompoundTag beTag = new CompoundTag();
            SkyblockType type = entity.getBlockType();
            if (type != null) {
                beTag.putString("blockType", type.name());
            }
            SkyblockPass pass = entity.getPass();
            if (pass != null) {
                beTag.putString("pass", pass.name());
            }

            stack.getOrCreateTag().put("BlockEntityTag", beTag);

            return stack;
        }

        return new ItemStack(InitItems.STORMY_BLOCK.get());
    }
}
