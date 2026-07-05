package com.noodlegamer76.shadered.network.lightemitterpayload;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.entity.GameObject;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class LightEmitterHandler {

    public static void handle(LightEmitterPayload payload, IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        BlockPos pos = payload.pos();
        float red = payload.red();
        float green = payload.green();
        float blue = payload.blue();
        float radius = payload.radius();
        if (!isValid(level, player, pos)) return;

        if (!(level.getBlockEntity(pos) instanceof LightBulbEntity lightBulb)) return;

        lightBulb.setRadius(radius);
        lightBulb.setColor(red, green, blue);
    }

    protected static boolean isValid(Level level, Player pPlayer, BlockPos pos) {
        return level.getBlockState(pos).is(InitBlocks.LIGHT_BULB.get()) &&
                pPlayer.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }
}
