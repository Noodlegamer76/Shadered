package com.noodlegamer76.shadered.client.renderer.block.painting;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.SkyblockPainting;
import com.noodlegamer76.shadered.entity.block.painting.SkyblockPaintingEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class SkyblockPaintingRenderer extends ComplexPaintingRenderer<SkyblockPaintingEntity> {
    public SkyblockPaintingRenderer(BlockEntityRendererProvider.Context context) {
        super(
                new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "painting")),
                new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "skyblock_painting"))
        );
    }
}
