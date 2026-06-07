package com.noodlegamer76.shadered.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.Illusorite;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class IllusoriteRenderer<T extends Illusorite & GeoAnimatable> extends GeoItemRenderer<T> {

    public IllusoriteRenderer() {
        super(new DefaultedItemGeoModel<>(
                ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "illusorite_ore")));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        SkyblockType type = SkyblockHolderBlockItem.getSkyblockType(currentItemStack);
        SkyblockPass pass = SkyblockHolderBlockItem.getSkyblockPass(currentItemStack);
        return ModRenderTypes.getSkyblockRenderType(type, pass);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        float renderTick = partialTick + Minecraft.getInstance().levelRenderer.getTicks();
        poseStack.translate(0, Mth.sin(renderTick * 0.05f) / 5, 0);

        Quaternionf spin = new Quaternionf();

        spin.rotateY(renderTick * 0.05f);
        spin.rotateX(renderTick * 0.035f);
        spin.rotateZ(renderTick * 0.02f);

        poseStack.mulPose(spin);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}