package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.item.IllusoriteOreRenderer;
import com.noodlegamer76.shadered.client.renderer.item.SkyblockItemRenderer;
import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class SkyblockItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final SkyblockItemTypes itemType;

    public SkyblockItem(Block block, Properties properties, SkyblockItemTypes itemType) {
        super(block, properties);
        this.itemType = itemType;
    }

    public SkyblockItemTypes getItemType() {
        return itemType;
    }

    public SkyblockType getType() {
        return SkyblockRegistry.getType(itemType);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private SkyblockItemRenderer<?> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new SkyblockItemRenderer<>();

                return this.renderer;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                @Nullable Level level,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        CompoundTag tag = stack.getTag();

        SkyblockPass pass = SkyblockPass.NORMAL;

        if (tag != null && tag.contains("BlockEntityTag")) {
            CompoundTag beTag = tag.getCompound("BlockEntityTag");

            if (beTag.contains("pass")) {
                String passName = beTag.getString("pass");

                if (!passName.isEmpty()) {
                    try {
                        pass = SkyblockPass.valueOf(passName);
                    } catch (IllegalArgumentException ignored) {
                        pass = SkyblockPass.NORMAL;
                    }
                }
            }
        }

        tooltip.add(
                Component.translatable(
                        "tooltip.shadered.pass",
                        pass.getDisplayName()
                ).withStyle(ChatFormatting.YELLOW)
        );

        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return this.getDescriptionId();
    }

    @Override
    public String getDescriptionId() {
        return itemType.getTranslationKey();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}