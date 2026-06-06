package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.block.IllusoriteOreBlock;
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

public class SkyblockHolderItem extends BlockItem {
    private static final SkyblockType DEFAULT_TYPE = SkyblockType.STORMY;
    private static final SkyblockPass DEFAULT_PASS = SkyblockPass.NORMAL;

    public SkyblockHolderItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                @Nullable Level level,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("BlockEntityTag")) {
            CompoundTag beTag = tag.getCompound("BlockEntityTag");

            SkyblockType type = DEFAULT_TYPE;

            if (beTag.contains("blockType")) {
                String typeName = beTag.getString("blockType");

                if (!typeName.isEmpty()) {
                    try {
                        type = SkyblockType.valueOf(typeName);
                    } catch (IllegalArgumentException ignored) {
                        type = DEFAULT_TYPE;
                    }
                }
            }

            tooltip.add(
                    Component.translatable(
                            "tooltip.shadered.type",
                            type.getDisplayName()
                    ).withStyle(ChatFormatting.YELLOW)
            );

            SkyblockPass pass = DEFAULT_PASS;

            if (beTag.contains("pass")) {
                String passName = beTag.getString("pass");

                if (!passName.isEmpty()) {
                    try {
                        pass = SkyblockPass.valueOf(passName);
                    } catch (IllegalArgumentException ignored) {
                        pass = DEFAULT_PASS;
                    }
                }
            }

            tooltip.add(
                    Component.translatable(
                            "tooltip.shadered.pass",
                            pass.getDisplayName()
                    ).withStyle(ChatFormatting.YELLOW)
            );
        } else {
            tooltip.add(
                    Component.translatable(
                            "tooltip.shadered.type",
                            DEFAULT_TYPE.getDisplayName()
                    ).withStyle(ChatFormatting.YELLOW)
            );

            tooltip.add(
                    Component.translatable(
                            "tooltip.shadered.pass",
                            DEFAULT_PASS.getDisplayName()
                    ).withStyle(ChatFormatting.YELLOW)
            );
        }

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
}