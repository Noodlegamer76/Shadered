package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.puddle.PuddleCubeRenderer;
import com.noodlegamer76.shadered.client.renderer.puddle.PuddleInfo;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class TestItem extends Item {
    //item i use to trigger stuff in the mod
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            PuddleCubeRenderer.puddles.clear();
            PuddleInfo info = new PuddleInfo();
            Color color = new Color(112, 0, 0, 255);
            info.setColor(color);
            PuddleCubeRenderer.puddles.add(info);
        }
        return super.use(pLevel, pPlayer, pUsedHand);

    }

    public static void makeBlood() {
        PuddleCubeRenderer.puddles.clear();
        PuddleInfo info = new PuddleInfo();
        Color color = new Color(112, 0, 0, 255);
        info.setColor(color);
        PuddleCubeRenderer.puddles.add(info);
    }
}
