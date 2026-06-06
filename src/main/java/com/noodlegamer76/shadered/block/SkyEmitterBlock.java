package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.client.ClientHooks;
import com.noodlegamer76.shadered.item.SkyblockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SkyEmitterBlock extends Skyblock implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 8, 16);

    public SkyEmitterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SkyEmitterEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof SkyEmitterEntity skyEmitterBlock) {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            if (stack.getItem() instanceof SkyblockItem skyblockItem) {
                skyEmitterBlock.setType(skyblockItem.getType());
                return InteractionResult.SUCCESS;
            }
            else {
                if (pLevel.isClientSide) {
                    ClientHooks.openSkyEmitterScreen(skyEmitterBlock);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
