package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.IllusoriteOreBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class IllusoriteOreRenderer<T extends IllusoriteOreBlockEntity> implements BlockEntityRenderer<T> {
    public IllusoriteOreRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T animatable, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockBatchData data = animatable.getBlockType().getData();
        if (data == null) {
            return;
    }
        poseStack.pushPose();
        poseStack.scale(0.98f, 0.98f, 0.98f);
        poseStack.translate(0.01f, 0.01f, 0.01f);
        data.add(
                animatable.getPass(),
                animatable.getBlockPos(),
                new Matrix4f(poseStack.last().pose()),
                false,
                1.0f
        );
        poseStack.popPose();
    }
}
