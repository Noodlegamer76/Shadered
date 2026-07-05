package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkyblockHolderItem extends Item {
    private static final SkyblockType DEFAULT_TYPE = SkyblockType.STORMY;
    private static final SkyblockPass DEFAULT_PASS = SkyblockPass.NORMAL;

    public SkyblockHolderItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                @Nullable Item.TooltipContext context,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        SkyblockType type = getSkyblockType(stack);
        SkyblockPass pass = getSkyblockPass(stack);

        tooltip.add(
                Component.translatable(
                        "tooltip.shadered.type",
                        type.getDisplayName()
                ).withStyle(ChatFormatting.YELLOW)
        );

        tooltip.add(
                Component.translatable(
                        "tooltip.shadered.pass",
                        pass.getDisplayName()
                ).withStyle(ChatFormatting.YELLOW)
        );

        super.appendHoverText(stack, context, tooltip, flag);
    }

    public static ItemStack create(SkyblockType type, SkyblockPass pass, Item item) {
        ItemStack stack = new ItemStack(item);

        stack.set(InitDataComponents.SKYBLOCK_DATA.get(), new SkyblockData(type, pass));

        return stack;
    }

    public static SkyblockType getSkyblockType(ItemStack stack) {
        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());
        if (data != null) {
            return data.type();
        }

        return DEFAULT_TYPE;
    }

    public static SkyblockPass getSkyblockPass(ItemStack stack) {
        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());
        if (data != null) {
            return data.pass();
        }

        return DEFAULT_PASS;
    }
}