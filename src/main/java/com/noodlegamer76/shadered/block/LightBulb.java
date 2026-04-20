package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.ClientHooks;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class LightBulb extends Block implements EntityBlock {
    public LightBulb(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LightBulbEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof LightBulbEntity lightBulb && pLevel.isClientSide) {
            ClientHooks.openLightEmitterScreen(lightBulb);
            return InteractionResult.SUCCESS;
        }
        if (pLevel.getBlockEntity(pPos) instanceof LightBulbEntity lightBulb) {
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
