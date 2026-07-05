package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.item.IllusoriteOreRenderer;
import com.noodlegamer76.shadered.client.renderer.item.SkyblockItemRenderer;
import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
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
                                @Nullable TooltipContext context,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());
        SkyblockPass pass = data != null ? data.pass() : SkyblockPass.NORMAL;

        tooltip.add(
                Component.translatable(
                        "tooltip.shadered.pass",
                        pass.getDisplayName()
                ).withStyle(ChatFormatting.YELLOW)
        );

        super.appendHoverText(stack, context, tooltip, flag);
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