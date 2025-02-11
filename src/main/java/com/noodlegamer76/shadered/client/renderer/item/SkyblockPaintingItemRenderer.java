package com.noodlegamer76.shadered.client.renderer.item;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.DynamicGeoItemRenderer;
import com.noodlegamer76.shadered.entity.block.SkyblockPainting;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import com.noodlegamer76.shadered.item.SkyblockPaintingItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL44;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SkyblockPaintingItemRenderer extends DynamicGeoItemRenderer<SkyblockPaintingItem> {
    public static final ResourceLocation FRAME_LOCATION = new ResourceLocation(ShaderedMod.MODID, "painting");
    public DefaultedBlockGeoModel<SkyblockPaintingItem> frame = new DefaultedBlockGeoModel<>(FRAME_LOCATION);

    public SkyblockPaintingItemRenderer() {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(ShaderedMod.MODID, "skyblock_painting")));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, SkyblockPaintingItem animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {


        int current = GlStateManager.getBoundFramebuffer();
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, RenderEventsForFbos.painting1Fbo);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, red, green, blue, alpha);
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);

        super.actuallyRender(poseStack, animatable, frame.getBakedModel(frame.getModelResource(animatable, this)), renderType, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, red, green, blue, alpha);
    }


    @Override
    protected @Nullable RenderType getRenderTypeOverrideForBone(GeoBone bone, SkyblockPaintingItem animatable, ResourceLocation texturePath, MultiBufferSource bufferSource, float partialTick) {
        if (bone.getName().equals("background")) {
            return ModRenderTypes.STORMY;
        }
        else if (bone.getName().equals("window")) {
            return ModRenderTypes.PAINTING1;
        }
        else if (bone.getName().equals("painting")) {
            return RenderType.entityCutout(new ResourceLocation(ShaderedMod.MODID, "textures/block/painting.png"));
        }
        return RenderType.entityCutout(texturePath);
    }
}
