package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class SkyblockItem extends BlockItem {
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
}