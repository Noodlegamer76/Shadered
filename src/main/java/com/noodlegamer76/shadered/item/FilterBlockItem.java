package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.item.FilterBlockItemRenderer;
import com.noodlegamer76.shadered.client.renderer.item.IllusoriteRenderer;
import com.noodlegamer76.shadered.client.util.PassType;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
                if (this.renderer == null)
                    this.renderer = new FilterBlockItemRenderer<>();

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
        SkyblockPass pass = SkyblockHolderBlockItem.getSkyblockPass(stack);
        return "item.shadered.filter." + pass.name().toLowerCase();
    }

    public static ItemStack create(SkyblockPass pass, Item item) {
        ItemStack stack = new ItemStack(item);

        CompoundTag beTag = new CompoundTag();
        beTag.putString("pass", pass.name());

        stack.getOrCreateTag().put("BlockEntityTag", beTag);

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