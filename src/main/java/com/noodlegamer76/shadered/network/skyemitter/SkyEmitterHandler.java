package com.noodlegamer76.shadered.network.skyemitter;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.entity.GameObject;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class SkyEmitterHandler {

    public static void handle(SkyEmitterPacket payload, IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        float maxDistance = payload.maxDistance();
        float minDistance = payload.minDistance();
        float maxAlpha = payload.maxAlpha();
        BlockPos pos = payload.pos();
        if (!isValid(level, player, pos)) return;

        if (!(level.getBlockEntity(pos) instanceof SkyEmitterEntity skyEmitter)) return;

        skyEmitter.setAlpha(maxAlpha);
        skyEmitter.setMaximumRange(maxDistance);
        skyEmitter.setMinimumRange(minDistance);
    }

    protected static boolean isValid(Level level, Player pPlayer, BlockPos pos) {
        return level.getBlockState(pos).is(InitBlocks.SKY_EMITTER.get()) &&
                pPlayer.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }
}
