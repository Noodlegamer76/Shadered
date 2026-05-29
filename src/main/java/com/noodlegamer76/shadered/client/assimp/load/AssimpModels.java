package com.noodlegamer76.shadered.client.assimp.load;

import com.noodlegamer76.shadered.client.assimp.McModel;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AssimpModels {
    private static final Map<ResourceLocation, McModel> MODELS = new HashMap<>();

    public static McModel getModel(ResourceLocation resourceLocation) {
        return MODELS.get(resourceLocation);
    }

    public static void clear() {
        MODELS.clear();
    }

    public static void registerModel(ResourceLocation resourceLocation, McModel model) {
        MODELS.put(resourceLocation, model);
    }
}
