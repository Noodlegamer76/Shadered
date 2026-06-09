package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.FilterBlockEntity;
import com.noodlegamer76.shadered.entity.block.SkyblockEntity;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import com.noodlegamer76.shadered.item.SkyblockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

public class FilterBlock extends Block implements EntityBlock
{
    public FilterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FilterBlockEntity(pPos, pState);
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

        if (stack.getItem() instanceof SkyblockHolderBlockItem) {
            CompoundTag tag = stack.getTag();
            if (tag == null || !tag.contains("BlockEntityTag")) return;

            CompoundTag beTag = tag.getCompound("BlockEntityTag");

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
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof FilterBlockEntity entity) {
            ItemStack stack = new ItemStack(asItem());

            CompoundTag beTag = new CompoundTag();

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
