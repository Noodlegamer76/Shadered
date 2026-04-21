package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.ComplexPassRenderer;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.glass.GlassChannel;
import com.noodlegamer76.shadered.client.util.shader.lights.Light;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import com.noodlegamer76.shadered.core.component.components.LightComponent;
import com.noodlegamer76.shadered.entity.GameObject;
import com.noodlegamer76.shadered.entity.InitEntities;
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
        if (!pLevel.isClientSide) {
            GameObject gameObject = new GameObject(
                    InitEntities.GAME_OBJECT.get(), pLevel
            );

            LightComponent lightComponent = new LightComponent(gameObject);
            lightComponent.setColor(1.0f, 0.0f, 1.0f);
            lightComponent.setRadius(10);

            gameObject.addComponent(lightComponent);

            gameObject.setPos(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
            pLevel.addFreshEntity(gameObject);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
