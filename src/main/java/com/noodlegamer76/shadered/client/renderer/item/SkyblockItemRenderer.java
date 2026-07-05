package com.noodlegamer76.shadered.client.renderer.item;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import com.noodlegamer76.shadered.item.SkyblockItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SkyblockItemRenderer<T extends SkyblockItem> extends GeoItemRenderer<T> {
    public SkyblockItemRenderer() {
        super(new DefaultedItemGeoModel<>(
                ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "illusorite_ore")));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        SkyblockType type = SkyblockHolderBlockItem.getSkyblockType(currentItemStack);
        SkyblockPass pass = SkyblockHolderBlockItem.getSkyblockPass(currentItemStack);
        return ModRenderTypes.getSkyblockRenderType(type, pass);
    }
}
