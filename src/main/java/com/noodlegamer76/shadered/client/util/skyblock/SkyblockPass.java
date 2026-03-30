package com.noodlegamer76.shadered.client.util.skyblock;

import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.renderer.ShaderInstance;

import java.util.function.Supplier;

public enum SkyblockPass {
    NORMAL(() -> RegisterShaders.skyblock),
    INVERTED(() -> RegisterShaders.skyblockInvert),
    POSTERIZE(() -> RegisterShaders.skyblockPosterize),
    GRAYSCALE(() -> RegisterShaders.skyblockGrayscale),
    CHROMATIC_ABERRATION(() -> RegisterShaders.skyblockChromaticAberration),
    SCREEN(() -> RegisterShaders.skyblockScreen);

    public final Supplier<ShaderInstance> shader;

    SkyblockPass(Supplier<ShaderInstance> shader) {
        this.shader = shader;
    }
}
