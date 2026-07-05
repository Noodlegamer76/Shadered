package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.ClientHooks;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
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
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof LightBulbEntity lightBulb && level.isClientSide) {
            ClientHooks.openLightEmitterScreen(lightBulb);
            return InteractionResult.SUCCESS;
        }
        if (level.getBlockEntity(pos) instanceof LightBulbEntity lightBulb) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
