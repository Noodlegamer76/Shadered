package com.noodlegamer76.shadered.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.item.FilterBlockItem;
import com.noodlegamer76.shadered.item.Illusorite;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.Map;

public class FilterBlockItemRenderer<T extends FilterBlockItem & GeoAnimatable> extends GeoItemRenderer<T> {
    private static final Map<SkyblockPass, ResourceLocation> TEXTURE_MAP = Map.of(
            SkyblockPass.NORMAL,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_normal.png"),
            SkyblockPass.BLUEPRINT,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_blueprint.png"),
            SkyblockPass.SCREEN,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_screen.png"),
            SkyblockPass.CHROMATIC_ABERRATION,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_chromatic_aberration.png"),
            SkyblockPass.GAMEBOY,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_gameboy.png"),
            SkyblockPass.GRAYSCALE,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_grayscale.png"),
            SkyblockPass.INVERTED,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_inverted.png"),
            SkyblockPass.POSTERIZE,
            ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/skyblock_filter_posterize.png")
    );

    public FilterBlockItemRenderer() {
        super(new DefaultedItemGeoModel<>(
                ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "illusorite_ore")));
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return super.getTextureLocation(animatable);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        SkyblockPass pass = SkyblockHolderBlockItem.getSkyblockPass(currentItemStack);
        SkyblockRenderer.filterData.add(pass, new BlockPos(-99999, -99999, -99999), poseStack.last().pose(), false, 1.0F);

        //super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected void renderInGui(ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = getCurrentItemStack();
        SkyblockPass pass = SkyblockHolderBlockItem.getSkyblockPass(stack);

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);

        poseStack.scale(0.5f, 0.5f, 0.5f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f mat = pose.pose();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        float size = 1f;

        RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/item/filter_block_background.png"));

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        builder.vertex(mat, -size, size, 0).uv(0, 0).endVertex();
        builder.vertex(mat, -size, -size, 0).uv(0, 1).endVertex();
        builder.vertex(mat, size, -size, 0).uv(1, 1).endVertex();
        builder.vertex(mat, size, size, 0).uv(1, 0).endVertex();

        BufferUploader.drawWithShader(builder.end());

        ResourceLocation texture = TEXTURE_MAP.get(pass);
        RenderSystem.setShaderTexture(0, texture);

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        builder.vertex(mat, -size, size, 0).uv(0, 0).endVertex();
        builder.vertex(mat, -size, -size, 0).uv(0, 1).endVertex();
        builder.vertex(mat, size, -size, 0).uv(1, 1).endVertex();
        builder.vertex(mat, size, size, 0).uv(1, 0).endVertex();

        BufferUploader.drawWithShader(builder.end());

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();

        poseStack.popPose();
    }
}