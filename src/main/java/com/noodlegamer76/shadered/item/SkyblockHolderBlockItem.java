package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
                                @Nullable Level level,
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

        super.appendHoverText(stack, level, tooltip, flag);
    }

    public static ItemStack create(SkyblockType type, SkyblockPass pass, Item item) {
        ItemStack stack = new ItemStack(item);

        CompoundTag beTag = new CompoundTag();
        beTag.putString("blockType", type.name());
        beTag.putString("pass", pass.name());

        stack.getOrCreateTag().put("BlockEntityTag", beTag);

        return stack;
    }

    public static SkyblockType getSkyblockType(ItemStack stack) {
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("BlockEntityTag")) {
            CompoundTag beTag = tag.getCompound("BlockEntityTag");

            if (beTag.contains("blockType")) {
                String typeName = beTag.getString("blockType");

                if (!typeName.isEmpty()) {
                    try {
                        return SkyblockType.valueOf(typeName);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }

        return DEFAULT_TYPE;
    }

    public static SkyblockPass getSkyblockPass(ItemStack stack) {
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("BlockEntityTag")) {
            CompoundTag beTag = tag.getCompound("BlockEntityTag");

            if (beTag.contains("pass")) {
                String passName = beTag.getString("pass");

                if (!passName.isEmpty()) {
                    try {
                        return SkyblockPass.valueOf(passName);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }

        return DEFAULT_PASS;
    }
}