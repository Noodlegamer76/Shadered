package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.ModRenderTypes;
import com.noodlegamer76.shadered.client.util.DynamicGeoBlockRenderer;
import com.noodlegamer76.shadered.entity.block.SkyblockPainting;
import com.noodlegamer76.shadered.event.RenderEventsForFbos;
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

public class SkyblockPaintingRenderer extends DynamicGeoBlockRenderer<SkyblockPainting> {
    public static final ResourceLocation FRAME_LOCATION = new ResourceLocation(ShaderedMod.MODID, "painting");
    public DefaultedBlockGeoModel<SkyblockPainting> frame = new DefaultedBlockGeoModel<>(FRAME_LOCATION);

    public SkyblockPaintingRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(ShaderedMod.MODID, "skyblock_painting")));
    }

    public static boolean test;

    @Override
    public void actuallyRender(PoseStack poseStack, SkyblockPainting animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if (animatable.getLevel() == null) {
            return;
        }

        poseStack.pushPose();
        if (getFacing(animatable) == Direction.UP) {
            poseStack.translate(0, 0.5, -0.5);
        }
        if (getFacing(animatable) == Direction.DOWN) {
            poseStack.translate(0, 0.5, 0.5);
        }
        switch (getFacing(animatable)) {
            case UP -> poseStack.translate(0, -1, 0);
            case DOWN -> poseStack.translate(0, 1, 0);
            case NORTH -> poseStack.translate(0, 0, 1);
            case SOUTH -> poseStack.translate(0, 0, -1);
            case WEST -> poseStack.translate(1, 0, 0);
            case EAST -> poseStack.translate(-1, 0, 0);
        }

        int current = GlStateManager.getBoundFramebuffer();
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, RenderEventsForFbos.painting1Fbo);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, red, green, blue, alpha);
        GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);

        poseStack.popPose();



        poseStack.pushPose();
        if (getFacing(animatable) == Direction.UP) {
            poseStack.translate(0, 0.5, -0.5);
        }
        if (getFacing(animatable) == Direction.DOWN) {
            poseStack.translate(0, 0.5, 0.5);
        }
        switch (getFacing(animatable)) {
            case UP -> poseStack.translate(0, -1, 0);
            case DOWN -> poseStack.translate(0, 1, 0);
            case NORTH -> poseStack.translate(0, 0, 1);
            case SOUTH -> poseStack.translate(0, 0, -1);
            case WEST -> poseStack.translate(1, 0, 0);
            case EAST -> poseStack.translate(-1, 0, 0);
        }

        super.actuallyRender(poseStack, animatable, frame.getBakedModel(frame.getModelResource(animatable, this)), renderType, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    protected @Nullable RenderType getRenderTypeOverrideForBone(GeoBone bone, SkyblockPainting animatable, ResourceLocation texturePath, MultiBufferSource bufferSource, float partialTick) {

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
