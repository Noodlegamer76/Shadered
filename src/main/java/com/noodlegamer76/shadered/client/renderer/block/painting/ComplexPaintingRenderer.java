package com.noodlegamer76.shadered.client.renderer.block.painting;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import com.noodlegamer76.shadered.entity.block.painting.ComplexPaintingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;

public class ComplexPaintingRenderer<T extends ComplexPaintingEntity> extends DynamicGeoBlockRenderer<T> {
    private final DefaultedBlockGeoModel<T> frameModel;
    private final BakedGeoModel frameModelBaked;

    public ComplexPaintingRenderer(GeoModel<T> frameModel, GeoModel<T> model) {
        super(model);
        this.frameModel = (DefaultedBlockGeoModel<T>) frameModel;
        this.frameModelBaked = this.frameModel.getBakedModel(this.frameModel.getModelResource(animatable));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.pushPose();
        super.actuallyRender(poseStack, animatable, frameModelBaked, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.popPose();

        poseStack.pushPose();
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.popPose();

    }

    public DefaultedBlockGeoModel<T> getFrameModel() {
        return frameModel;
    }

    @Override
    protected @Nullable RenderType getRenderTypeOverrideForBone(GeoBone bone, T animatable, ResourceLocation texturePath, MultiBufferSource bufferSource, float partialTick) {
        return switch (bone.getName()) {
            case "background_space" -> ModRenderTypes.SPACE;
            case "background_stormy" -> ModRenderTypes.STORMY;
            case "window" -> ModRenderTypes.WINDOW;
            case "painting" -> RenderType.entityCutout(frameModel.getTextureResource(animatable));
            default -> ModRenderTypes.ENTITY_IN_WINDOW;
        };
    }
}
