package com.noodlegamer76.shadered.mixin;

import com.mojang.blaze3d.shaders.Program;
import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatchRegistry;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Mixin(Program.class)
public class ProgramMixin {

    @Redirect(
            method = "compileShaderInternal",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/commons/io/IOUtils;toString(Ljava/io/InputStream;Ljava/nio/charset/Charset;)Ljava/lang/String;"
            )
    )
    private static String shadered$patchSource(
            InputStream stream,
            Charset charset,
            Program.Type pType,
            String pName
    ) throws IOException {
        String source = IOUtils.toString(stream, charset);
        return ShaderPatchRegistry.apply(
                pName,
                source,
                new ShaderContext(pName, pType == Program.Type.FRAGMENT)
        );
    }
}