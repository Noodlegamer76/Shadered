package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.entity.block.SpaceCompressorBlockEntity;
import com.noodlegamer76.shadered.util.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
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

        if (entity instanceof SpaceCompressorBlockEntity spaceCompressor) {
            stack.set(ModDataComponents.COMPRESSOR_POS.get(), clicked.immutable());

            spaceCompressor.setPos1(null);
            spaceCompressor.setPos2(null);
            spaceCompressor.setChanged();

            level.sendBlockUpdated(clicked, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);

            return InteractionResult.SUCCESS;
        }

        if (stack.has(ModDataComponents.COMPRESSOR_POS.get())) {

            BlockPos compressorPos = stack.get(ModDataComponents.COMPRESSOR_POS.get());

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

                    int dx = Math.clamp(clicked.getX() - pos1.getX(), -maxDelta, maxDelta);
                    int dy = Math.clamp(clicked.getY() - pos1.getY(), -maxDelta, maxDelta);
                    int dz = Math.clamp(clicked.getZ() - pos1.getZ(), -maxDelta, maxDelta);

                    spaceCompressor.setPos2(pos1.offset(dx, dy, dz));
                    spaceCompressor.setChanged();
                    level.sendBlockUpdated(compressorPos, spaceCompressor.getBlockState(), spaceCompressor.getBlockState(), 3);

                    stack.remove(ModDataComponents.COMPRESSOR_POS.get());
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.useOn(context);
    }
}