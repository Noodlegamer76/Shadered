package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.item.IllusoriteRenderer;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class Illusorite extends SkyblockHolderItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Illusorite(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private IllusoriteRenderer<?> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new IllusoriteRenderer<>();

                return this.renderer;
            }
        });
    }

    @Override
    public void inventoryTick(ItemStack stack,
                              Level level,
                              Entity entity,
                              int slot,
                              boolean selected) {

        if (level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();

            long nextChange = tag.getLong("NextChange");

            if (level.getGameTime() >= nextChange) {
                randomize(stack, level.random);

                tag.putLong(
                        "NextChange",
                        level.getGameTime() + 20 + level.random.nextInt(21)
                );

                onShift(stack, level);
            }
        }
    }

    private static void onShift(ItemStack stack, Level level) {
    }

    public static void randomize(ItemStack stack, RandomSource random) {
        SkyblockType[] types = SkyblockType.values();
        SkyblockPass[] passes = SkyblockPass.values();

        SkyblockType type = types[random.nextInt(types.length)];
        SkyblockPass pass = passes[random.nextInt(passes.length)];

        CompoundTag beTag = stack.getOrCreateTagElement("BlockEntityTag");
        beTag.putString("blockType", type.name());
        beTag.putString("pass", pass.name());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
