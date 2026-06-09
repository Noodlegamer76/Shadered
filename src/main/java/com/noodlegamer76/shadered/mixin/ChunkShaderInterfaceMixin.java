package com.noodlegamer76.shadered.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.noodlegamer76.shadered.client.util.shader.EmbeddiumFilterSamplers;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import me.jellysquid.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChunkShaderInterface.class, remap = false)
public class ChunkShaderInterfaceMixin {

    @Inject(method = "setupState", at = @At("TAIL"))
    private void shadered$uploadLightsAndFilters(CallbackInfo ci) {
        int program = GL20.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        if (program == 0) return;

        LightUploader.upload(program);
        EmbeddiumFilterSamplers.upload(program);
    }
}