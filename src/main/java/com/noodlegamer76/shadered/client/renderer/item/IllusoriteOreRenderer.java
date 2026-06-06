package com.noodlegamer76.shadered.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.InitItems;
import com.noodlegamer76.shadered.item.SkyblockHolderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.cache.object.GeoBone;

import javax.annotation.Nullable;

public class IllusoriteOreRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
    private GeoBone currentBone;
    public static final ResourceLocation DEEPSLATE_ILLUSORITE_ORE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/deepslate_illusorite_ore.png");
    public static final ResourceLocation ILLUSORITE_ORE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/illusorite_ore.png");

    public IllusoriteOreRenderer() {
        super(new DefaultedItemGeoModel<>(
                ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "illusorite_ore")));
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        if (animatable == InitItems.DEEPSLATE_ILLUSORITE_ORE.get()) {
            return DEEPSLATE_ILLUSORITE_ORE_TEXTURE;
        }
        return ILLUSORITE_ORE_TEXTURE;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(
            PoseStack poseStack,
            T animatable,
            GeoBone bone,
            RenderType renderType,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {

        if (bone.getName().equals("Inner")) {
            SkyblockType type = SkyblockHolderItem.getSkyblockType(getCurrentItemStack());
            SkyblockPass pass = SkyblockHolderItem.getSkyblockPass(getCurrentItemStack());
            renderType = ModRenderTypes.getSkyblockRenderType(type, pass);
            buffer = bufferSource.getBuffer(renderType);
        }
        else {
            buffer = bufferSource.getBuffer(renderType);
        }

        super.renderRecursively(
                poseStack,
                animatable,
                bone,
                renderType,
                bufferSource,
                buffer,
                isReRender,
                partialTick,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha);
    }
}