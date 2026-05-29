package com.noodlegamer76.shadered.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.core.component.Component;
import com.noodlegamer76.shadered.core.component.components.RenderableComponent;
import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class GameObjectRenderer extends EntityRenderer<GameObject> {
    public static final ResourceLocation DIRT = ResourceLocation.withDefaultNamespace("textures/block/dirt.png");

    public GameObjectRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        shadowRadius = 0.5F;
        shadowStrength = 0.5F;
    }

    @Override
    public void render(GameObject entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack = new PoseStack();
        poseStack.pushPose();
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();

        poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        poseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Vector3f lv = entity.getLinearVelocity();

        Vec3 position = entity.getPosition(partialTick);

        poseStack.translate(position.x, position.y, position.z);

        Quaternionf interpolatedRot = new Quaternionf(entity.prevRotation).slerp(entity.getRotation(), partialTick);
        poseStack.mulPose(interpolatedRot);

        poseStack.translate(-0.5f, -0.5f, -0.5f);

        for (Component component: entity.getComponentManager().getComponents().values()) {
            if (component instanceof RenderableComponent renderable) {
                renderable.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            }
        }

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(GameObject pEntity) {
        return DIRT;
    }
}
