package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.Maxwell;
import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.client.assimp.McModel;
import com.noodlegamer76.shadered.client.assimp.load.AssimpModels;
import com.noodlegamer76.shadered.client.renderer.assimp.AssimpRenderer;
import com.noodlegamer76.shadered.client.renderer.assimp.RenderableModel;
import com.noodlegamer76.shadered.entity.block.MaxwellEntity;
import com.noodlegamer76.shadered.entity.block.RenderTester;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

public class MaxwellRenderer<T extends RenderTester> implements BlockEntityRenderer<MaxwellEntity> {
    public MaxwellRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MaxwellEntity pBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        McModel model = AssimpModels.getModel(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "models/complex/maxwell.glb"));

        if (model == null) {
            System.out.println("Model not found");
            return;
        }

        if (pBlockEntity.getModel() == null) {
            RenderableModel renderableModel = new RenderableModel();
            for (AssimpModel assimpModel: model.getModels()) {
                renderableModel.add(assimpModel);
            }
            pBlockEntity.setModel(renderableModel);
        }
        BlockState state = pBlockEntity.getBlockState();

        poseStack = new PoseStack();
        poseStack.translate(pBlockEntity.getBlockPos().getX(), pBlockEntity.getBlockPos().getY(), pBlockEntity.getBlockPos().getZ());
        poseStack.translate(0.5f, 0.0f, 0.5f);


        Direction dir = state.getValue(Maxwell.FACING);

        if (dir == Direction.NORTH) {
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
        } else if (dir == Direction.SOUTH) {
            poseStack.mulPose(Axis.YP.rotationDegrees(270.0f));
        } else if (dir == Direction.WEST) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        } else if (dir == Direction.EAST) {
            poseStack.mulPose(Axis.YP.rotationDegrees(0));
        }

        poseStack.scale(0.075f, 0.075f, 0.075f);
        pBlockEntity.getModel().modelMatrix = new Matrix4f(poseStack.last().pose());
        AssimpRenderer.getInstance().addModel(pBlockEntity.getModel());
    }
}
