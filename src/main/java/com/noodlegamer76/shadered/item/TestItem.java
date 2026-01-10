package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.mixin.accessor.ItemBlockRenderTypesAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3i;

import java.util.Map;

public class TestItem extends Item {
    //item I use to trigger stuff in the mod
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            int radius = 100;
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            Vector3i origin = new Vector3i((int) pPlayer.getX(), (int) pPlayer.getY(), (int) pPlayer.getZ());
            for (int x = origin.x - radius; x <= origin.x + radius; x++) {
                for (int y = origin.y - radius; y <= origin.y + radius; y++) {
                    for (int z = origin.z - radius; z <= origin.z + radius; z++) {
                        pos.set(x, y, z);
                        if (pLevel.isInWorldBounds(pos)) {
                            int random = (int) (Math.random() * 6);
                            Block block = switch (random) {
                                case 0 -> InitBlocks.SPACE_BLOCK.get();
                                case 1 -> InitBlocks.OCEAN_BLOCK.get();
                                case 2 -> InitBlocks.STORMY_BLOCK.get();
                                case 3 -> InitBlocks.END_SKY_BLOCK.get();
                                case 4 -> InitBlocks.ECLIPSE_BLOCK.get();
                                default -> InitBlocks.END_BLOCK.get();
                            };
                            BlockState state = block.defaultBlockState();
                            pLevel.setBlockAndUpdate(pos, state);
                        }
                    }
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
