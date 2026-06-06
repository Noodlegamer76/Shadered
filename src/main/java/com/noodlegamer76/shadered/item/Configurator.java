package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.entity.block.SpaceCompressorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

        BlockPos clicked = context.getClickedPos();
        BlockEntity entity = level.getBlockEntity(clicked);
        CompoundTag tag = stack.getOrCreateTag();

        if (entity instanceof SpaceCompressorBlockEntity spaceCompressor) {
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", clicked.getX());
            pos.putInt("y", clicked.getY());
            pos.putInt("z", clicked.getZ());

            tag.put("shadered:compressor", pos);

            spaceCompressor.setPos1(null);
            spaceCompressor.setPos2(null);
            spaceCompressor.setChanged();
            level.sendBlockUpdated(clicked, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);

            return InteractionResult.SUCCESS;
        }

        if (tag.contains("shadered:compressor")) {
            CompoundTag pos = tag.getCompound("shadered:compressor");
            BlockPos compressorPos = new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z"));

            if (level.getBlockEntity(compressorPos) instanceof SpaceCompressorBlockEntity spaceCompressor) {
                if (spaceCompressor.getPos1() == null) {
                    spaceCompressor.setPos1(clicked);
                    spaceCompressor.setChanged();
                    level.sendBlockUpdated(compressorPos, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);
                    return InteractionResult.SUCCESS;
                } else if (spaceCompressor.getPos2() == null) {
                    int sizeLimit = 16;
                    BlockPos pos1 = spaceCompressor.getPos1();
                    int maxDelta = sizeLimit - 1;

                    int dx = Math.max(-maxDelta, Math.min(clicked.getX() - pos1.getX(), maxDelta));
                    int dy = Math.max(-maxDelta, Math.min(clicked.getY() - pos1.getY(), maxDelta));
                    int dz = Math.max(-maxDelta, Math.min(clicked.getZ() - pos1.getZ(), maxDelta));

                    spaceCompressor.setPos2(pos1.offset(dx, dy, dz));
                    spaceCompressor.setChanged();
                    level.sendBlockUpdated(compressorPos, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);

                    tag.remove("shadered:compressor");
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.useOn(context);
    }
}
