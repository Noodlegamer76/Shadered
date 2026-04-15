package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.skyemitter.SkyEmitterType;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.client.ClientHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SkyEmitterBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 8, 16);

    public SkyEmitterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SkyEmitterEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof SkyEmitterEntity skyEmitterBlock) {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            if (stack.is(InitItems.ECLIPSE_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.ECLIPSE);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.SPACE_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.SPACE);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.FOREST_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.FOREST);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.STORMY_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.STORMY);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.END_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.END);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.IRIDIA_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.IRIDIA);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.OCEAN_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.OCEAN);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.LIGHT_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.LIGHT);
                return InteractionResult.SUCCESS;
            }
            else if (stack.is(InitItems.MIMIC_BLOCK.get())) {
                skyEmitterBlock.setType(SkyEmitterType.MIMIC);
                return InteractionResult.SUCCESS;
            } else {
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
