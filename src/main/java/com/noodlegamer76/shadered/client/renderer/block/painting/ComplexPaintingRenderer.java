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
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;

public class ComplexPaintingRenderer<T extends ComplexPaintingEntity> extends DynamicGeoBlockRenderer<T> {
    private final DefaultedBlockGeoModel<T> frameModel;

    public ComplexPaintingRenderer(GeoModel<T> frameModel, GeoModel<T> model) {
        super(model);
        this.frameModel = (DefaultedBlockGeoModel<T>) frameModel;
    }

    public DefaultedBlockGeoModel<T> getFrameModel() {
        return frameModel;
    }

    @Override
    protected boolean boneRenderOverride(PoseStack poseStack, GeoBone bone, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (bone.getName().equals("window")) {
            return true;
        }

        return false;
    }

    @Override
    protected @Nullable RenderType getRenderTypeOverrideForBone(GeoBone bone, T animatable, ResourceLocation texturePath, MultiBufferSource bufferSource, float partialTick) {
        return switch (bone.getName()) {
            case "background_space" -> ModRenderTypes.SPACE;
            case "background_stormy" -> ModRenderTypes.STORMY;
            default -> super.getRenderTypeOverrideForBone(bone, animatable, texturePath, bufferSource, partialTick);
        };
    }
}
