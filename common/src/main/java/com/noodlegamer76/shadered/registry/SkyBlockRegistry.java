package com.noodlegamer76.shadered.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class SkyBlockRegistry {
    private final String name;
    private final ResourceLocation textureFolder;

    public SkyBlockRegistry(String name, ResourceLocation textureFolder) {
        this.name = name;
        this.textureFolder = textureFolder;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getTextureFolder() {
        return textureFolder;
    }
}
