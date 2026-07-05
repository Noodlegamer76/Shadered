package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.item.IllusoriteRenderer;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
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
                              boolean isCurrentItem) {

        if (level.isClientSide) {
            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();

            long nextChange = tag.getLong("NextChange");

            if (level.getGameTime() >= nextChange) {
                randomize(stack, level.random);

                CustomData updatedCustomData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag updatedTag = updatedCustomData.copyTag();
                updatedTag.putLong(
                        "NextChange",
                        level.getGameTime() + 20 + level.random.nextInt(21)
                );
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(updatedTag));

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

        stack.set(InitDataComponents.SKYBLOCK_DATA.get(), new SkyblockData(type, pass));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}