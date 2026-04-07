package com.noodlegamer76.shadered.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {

    @Invoker(
            value = "renderSnowAndRain"
    )
    void noodleEngine$renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ);
}
