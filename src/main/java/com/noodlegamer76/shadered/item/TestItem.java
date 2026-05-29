package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.core.component.components.MeshComponent;
import com.noodlegamer76.shadered.entity.GameObject;
import com.noodlegamer76.shadered.entity.InitEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

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

            ResourceLocation modelLocation = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "models/complex/test.glb");
            MeshComponent meshComponent = new MeshComponent(gameObject);
            meshComponent.setModel(modelLocation);

            gameObject.addComponent(meshComponent);

            gameObject.setScale(new Vector3f(75, 75, 75));

            gameObject.setPos(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
            pLevel.addFreshEntity(gameObject);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
