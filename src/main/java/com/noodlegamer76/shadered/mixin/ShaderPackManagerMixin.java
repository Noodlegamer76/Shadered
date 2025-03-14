package com.noodlegamer76.shadered.mixin;

import com.noodlegamer76.shadered.compat.iris.ShaderInjector;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.shaderpack.ShaderPack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Iris.class)
public class ShaderPackManagerMixin {
    @Shadow(remap = false)
    private static ShaderPack currentPack;


    @Inject(method = "loadShaderpack", at = @At("TAIL"), remap = false)
    @Debug(export = true)
    private static void injectShaderPack(CallbackInfo ci) throws IOException {
        if (currentPack != null) {
            ShaderInjector.modifyShaderPack(currentPack);
        }
    }
}