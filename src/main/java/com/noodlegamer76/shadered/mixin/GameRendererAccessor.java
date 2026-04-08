package com.noodlegamer76.shadered.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {

    @Invoker(
            value = "bobHurt"
    )
    void shadered$invokeBobHurt(PoseStack pPoseStack, float pPartialTicks);

    @Invoker(
            value = "bobView"
    )
    void shadered$invokeBobView(PoseStack pPoseStack, float pPartialTicks);

    @Invoker(
            value = "getFov"
    )
    double shadered$invokeGetFov(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting);
}
