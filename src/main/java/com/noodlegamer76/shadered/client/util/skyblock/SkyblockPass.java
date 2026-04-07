package com.noodlegamer76.shadered.client.util.skyblock;

import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum SkyblockPass {
    NORMAL("skyblock"),
    INVERTED("skyblock_invert"),
    POSTERIZE("skyblock_posterize"),
    GRAYSCALE("skyblock_grayscale"),
    CHROMATIC_ABERRATION("skyblock_chromatic_aberration"),
    SCREEN("skyblock_screen"),
    BLUEPRINT("skyblock_blueprint"),
    GAMEBOY("skyblock_gameboy");

    public final String shaderName;

    SkyblockPass(String shaderName) {
        this.shaderName = shaderName;
    }

    public ShaderInstance getShader() {
        return RegisterShaders.get(shaderName);
    }
}
