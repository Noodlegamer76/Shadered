package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkyblockHolderBlockItem extends BlockItem {
    private static final SkyblockType DEFAULT_TYPE = SkyblockType.STORMY;
    private static final SkyblockPass DEFAULT_PASS = SkyblockPass.NORMAL;

    public SkyblockHolderBlockItem(Block block, Properties properties) {
        super(block, properties);
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
        return data != null ? data.type() : DEFAULT_TYPE;
    }

    public static SkyblockPass getSkyblockPass(ItemStack stack) {
        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());
        return data != null ? data.pass() : DEFAULT_PASS;
    }
}