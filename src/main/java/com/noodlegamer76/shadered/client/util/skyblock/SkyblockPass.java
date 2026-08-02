package com.noodlegamer76.shadered.client.util.skyblock;

import net.minecraft.network.chat.Component;

public enum SkyblockPass {
    NORMAL("skyblock"),
    INVERTED("skyblock_invert"),
    POSTERIZE("skyblock_posterize"),
    GRAYSCALE("skyblock_grayscale"),
    CHROMATIC_ABERRATION("skyblock_chromatic_aberration"),
    SCREEN("skyblock_screen"),
    BLUEPRINT("skyblock_blueprint"),
    GAMEBOY("skyblock_gameboy"),
    BACKGROUND("skyblock_background");

    public final String shaderName;
    private final String translationKey;

    SkyblockPass(String shaderName) {
        this.shaderName = shaderName;
        this.translationKey = "skyblock_pass.shadered." + name().toLowerCase();
    }

    public String getShaderName() {
        return shaderName;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }
}