package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.item.FilterBlockItemRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class FilterBlockItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FilterBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FilterBlockItemRenderer<?> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new FilterBlockItemRenderer<>();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                Item.TooltipContext context,
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
        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());
        SkyblockPass pass = data != null ? data.pass() : SkyblockPass.NORMAL;
        return "item.shadered.filter." + pass.name().toLowerCase();
    }

    public static ItemStack create(SkyblockPass pass, Item item) {
        ItemStack stack = new ItemStack(item);

        stack.set(InitDataComponents.SKYBLOCK_DATA.get(), new SkyblockData(SkyblockType.STORMY, pass));

        return stack;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}