package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.entity.block.SpaceCompressorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Configurator extends Item {
    public Configurator(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        if (level.isClientSide || stack.isEmpty()) return super.useOn(context);
        BlockEntity entity = level.getBlockEntity(context.getClickedPos());
        if (entity instanceof SpaceCompressorBlockEntity spaceCompressor) {
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", context.getClickedPos().getX());
            pos.putInt("y", context.getClickedPos().getY());
            pos.putInt("z", context.getClickedPos().getZ());

            stack.getOrCreateTag().put("shadered:pos", pos);
            spaceCompressor.setPos1(null);
            spaceCompressor.setPos2(null);
            spaceCompressor.setChanged();
            level.sendBlockUpdated(spaceCompressor.getBlockPos(), spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);
            return InteractionResult.SUCCESS;
        }
        else {
            CompoundTag pos = stack.getOrCreateTag().getCompound("shadered:pos");
            if (pos.isEmpty()) return super.useOn(context);
            BlockPos compressor = new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z"));
            BlockPos clicked = context.getClickedPos();

            if (level.getBlockEntity(compressor) instanceof SpaceCompressorBlockEntity spaceCompressor) {
                if (spaceCompressor.getPos1() == null) {
                    spaceCompressor.setPos1(clicked);
                    spaceCompressor.setChanged();
                    level.sendBlockUpdated(compressor, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);
                    return InteractionResult.SUCCESS;
                }
                else if (spaceCompressor.getPos2() == null) {
                    int sizeLimit = 16;
                    BlockPos pos1 = spaceCompressor.getPos1();

                    int maxDelta = sizeLimit - 1;

                    int dx = Math.max(-maxDelta, Math.min(clicked.getX() - pos1.getX(), maxDelta));
                    int dy = Math.max(-maxDelta, Math.min(clicked.getY() - pos1.getY(), maxDelta));
                    int dz = Math.max(-maxDelta, Math.min(clicked.getZ() - pos1.getZ(), maxDelta));

                    BlockPos newPos = pos1.offset(dx, dy, dz);
                    spaceCompressor.setPos2(newPos);
                    spaceCompressor.setChanged();
                    level.sendBlockUpdated(compressor, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);
                    stack.getOrCreateTag().remove("shadered:pos");
                    return InteractionResult.SUCCESS;
                }

            }
        }

        return super.useOn(context);
    }
}
