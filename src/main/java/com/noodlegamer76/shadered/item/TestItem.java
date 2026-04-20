package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import com.noodlegamer76.shadered.client.util.shader.lights.Light;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
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
            LightUploader.clearLights();
            Light light = new Light(
                    (float) pPlayer.getX(),
                    (float) pPlayer.getY(),
                    (float) pPlayer.getZ(),
                    (float) -1,
                    (float) 1,
                    (float) 1,
                    10
            );
            LightUploader.addLight(light);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
