package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestItem extends Item {
    //item i use to trigger stuff in the mod
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            SkyblockRenderer.glassRenderer.getGlassChannels().clear();
            GlassChannel channel = new GlassChannel(new BlockPos(-50, 50, 0).subtract(pPlayer.blockPosition()));
            SkyblockRenderer.glassRenderer.addGlassChannel(channel);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
