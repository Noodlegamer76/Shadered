package com.noodlegamer76.shadered.mixin;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatchRegistry;
import me.jellysquid.mods.sodium.client.gl.shader.ShaderLoader;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShaderLoader.class)
public class ShaderLoaderMixin {

    @Inject(
            method = "getShaderSource",
            at = @At("RETURN"),
            remap = false,
            cancellable = true
    )
    private static void shadered$patchSource(
            ResourceLocation name,
            CallbackInfoReturnable<String> cir
    ) {
        String source = cir.getReturnValue();
        String shaderName = name.getPath();
        boolean isFragment = shaderName.endsWith(".fsh");

        String patched = ShaderPatchRegistry.apply(
                shaderName,
                source,
                new ShaderContext(shaderName, isFragment)
        );

        cir.setReturnValue(patched);
    }
}