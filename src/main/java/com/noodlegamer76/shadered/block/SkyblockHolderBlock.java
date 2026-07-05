package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.SkyblockEntity;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import com.noodlegamer76.shadered.item.SkyblockItem;
import com.noodlegamer76.shadered.item.SkyblockData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());

        if (data != null) {
            entity.setBlockType(data.type() != null ? data.type() : SkyblockType.STORMY);
            entity.setPass(data.pass() != null ? data.pass() : SkyblockPass.NORMAL);
        }
        else if (stack.getItem() instanceof SkyblockItem skyblockItem) {
            entity.setBlockType(skyblockItem.getType());
            entity.setPass(SkyblockPass.NORMAL);
        }
        else {
            entity.setBlockType(SkyblockType.STORMY);
            entity.setPass(SkyblockPass.NORMAL);
        }

        ItemStack otherHand = ItemStack.EMPTY;
        if (placer != null) {
            InteractionHand placementHand = placer.getMainHandItem() == stack ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            otherHand = placer.getItemInHand(placementHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        }

        entity.onPlaced(placer, otherHand);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SkyblockEntity(pPos, pState);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof SkyblockHolderEntity entity) {
            ItemStack stack = new ItemStack(asItem());

            SkyblockType type = entity.getBlockType() != null ? entity.getBlockType() : SkyblockType.STORMY;
            SkyblockPass pass = entity.getPass() != null ? entity.getPass() : SkyblockPass.NORMAL;

            stack.set(InitDataComponents.SKYBLOCK_DATA.get(), new SkyblockData(type, pass));

            return stack;
        }

        return new ItemStack(InitItems.STORMY_BLOCK.get());
    }
}