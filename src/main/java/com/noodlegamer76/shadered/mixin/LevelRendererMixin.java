package com.noodlegamer76.shadered.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.ModRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void renderChunkLayer(RenderType pRenderType, PoseStack pPoseStack, double pCamX, double pCamY, double pCamZ, Matrix4f pProjectionMatrix);

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderChunkLayer(Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack;DDDLorg/joml/Matrix4f;)V",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    public void onRenderChunkLayer(PoseStack pPoseStack,
                                   float pPartialTick,
                                   long pFinishNanoTime,
                                   boolean pRenderBlockOutline,
                                   Camera pCamera,
                                   GameRenderer pGameRenderer,
                                   LightTexture pLightTexture,
                                   Matrix4f pProjectionMatrix,
                                   CallbackInfo ci) {
        Vec3 cameraPos = pCamera.getPosition();

        minecraft.getProfiler().popPush("shadered_eclipse");
        renderChunkLayer(ModRenderTypes.ECLIPSE, pPoseStack, cameraPos.x, cameraPos.y, cameraPos.z, pProjectionMatrix);

        minecraft.getProfiler().popPush("shadered_space");
        renderChunkLayer(ModRenderTypes.SPACE, pPoseStack, cameraPos.x, cameraPos.y, cameraPos.z, pProjectionMatrix);

        minecraft.getProfiler().popPush("shadered_ocean");
        renderChunkLayer(ModRenderTypes.OCEAN, pPoseStack, cameraPos.x, cameraPos.y, cameraPos.z, pProjectionMatrix);

        minecraft.getProfiler().popPush("shadered_stormy");
        renderChunkLayer(ModRenderTypes.STORMY, pPoseStack, cameraPos.x, cameraPos.y, cameraPos.z, pProjectionMatrix);

        minecraft.getProfiler().popPush("shadered_end_sky");
        renderChunkLayer(ModRenderTypes.END_SKY, pPoseStack, cameraPos.x, cameraPos.y, cameraPos.z, pProjectionMatrix);

        minecraft.getProfiler().popPush("shadered_end");
        renderChunkLayer(ModRenderTypes.END_BLOCK, pPoseStack, cameraPos.x, cameraPos.y, cameraPos.z, pProjectionMatrix);
    }
}
