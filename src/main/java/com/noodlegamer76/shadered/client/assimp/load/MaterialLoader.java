package com.noodlegamer76.shadered.client.assimp.load;

import com.noodlegamer76.shadered.client.assimp.AssimpMaterial;
import com.noodlegamer76.shadered.client.assimp.McModel;
import com.noodlegamer76.shadered.client.assimp.util.BlendMode;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.assimp.*;
import org.lwjgl.PointerBuffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public class MaterialLoader {
    public static Map<Integer, AssimpMaterial> loadMaterials(AIScene scene, McModel model) {
        Map<Integer, AssimpMaterial> materialMap = new HashMap<>();
        int numMaterials = scene.mNumMaterials();
        PointerBuffer materialPointers = scene.mMaterials();

        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(materialPointers.get(i));
            AIString path = AIString.calloc();

            ResourceLocation diffuseTexture = getTexture(aiMaterial, model, path, Assimp.aiTextureType_DIFFUSE);
            ResourceLocation normalTexture = getTexture(aiMaterial, model, path, Assimp.aiTextureType_NORMALS);
            ResourceLocation specularTexture = getTexture(aiMaterial, model, path, Assimp.aiTextureType_SPECULAR);
            ResourceLocation emissiveTexture = getTexture(aiMaterial, model, path, Assimp.aiTextureType_EMISSIVE);
            ResourceLocation heightTexture = getTexture(aiMaterial, model, path, Assimp.aiTextureType_HEIGHT);

            float[] opacity = {1.0f};
            Assimp.aiGetMaterialFloatArray(aiMaterial, Assimp.AI_MATKEY_OPACITY,
                    Assimp.aiTextureType_NONE, 0, opacity, new int[]{1});

            int[] blendFunc = {0};
            Assimp.aiGetMaterialIntegerArray(aiMaterial, Assimp.AI_MATKEY_BLEND_FUNC,
                    Assimp.aiTextureType_NONE, 0, blendFunc, new int[]{1});

            AIColor4D color = AIColor4D.create();
            int colorResult = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_DIFFUSE,
                    Assimp.aiTextureType_NONE, 0, color);

            float r = 1f, g = 1f, b = 1f, a = 1f;
            if (colorResult == Assimp.aiReturn_SUCCESS) {
                r = color.r();
                g = color.g();
                b = color.b();
                a = color.a();
            }

            BlendMode mode = BlendMode.OPAQUE;
            if (opacity[0] < 1.0f || a < 1.0f) {
                mode = BlendMode.ALPHA;
            }


            if (blendFunc[0] == 1) {
                mode = BlendMode.ADDITIVE;
            }

            float mixedAlpha = a * opacity[0];

            boolean isTransparent = opacity[0] < 1.0f || mode != BlendMode.OPAQUE;

            AssimpMaterial material = new AssimpMaterial();

            material.setDiffuseTexture(diffuseTexture);
            material.setNormalTexture(normalTexture);
            material.setSpecularTexture(specularTexture);
            material.setEmissiveTexture(emissiveTexture);
            material.setHeightTexture(heightTexture);
            material.setColor(r, g, b, mixedAlpha);
            material.setTransparent(isTransparent);
            material.setBlendMode(mode);

            materialMap.put(i, material);
            path.free();
        }
        return materialMap;
    }

    private static ResourceLocation getTexture(AIMaterial aiMaterial, McModel model, AIString path, int texture) {
        int texResult = Assimp.aiGetMaterialTexture(
                aiMaterial, texture, 0, path,
                (IntBuffer) null, null, null, null, null, null
        );

        ResourceLocation textureLocation = null;
        if (texResult == Assimp.aiReturn_SUCCESS) {
            String texturePath = path.dataString();
            if (texturePath.startsWith("*")) {
                textureLocation = model.getEmbeddedTexture(Integer.parseInt(texturePath.substring(1)));
            } else {
                String fileName = new java.io.File(texturePath).getName();
                textureLocation = ResourceLocation.fromNamespaceAndPath(
                        model.getResourceLocation().getNamespace(), "textures/models/" + fileName);
            }
        }

        return textureLocation;
    }

    private static boolean checkTextureForTransparency(ByteBuffer imageBuffer, int width, int height, int channels) {
        if (channels < 4) return false;

        for (int i = 0; i < width * height; i++) {
            int alpha = imageBuffer.get(i * 4 + 3) & 0xFF;
            if (alpha < 250) {
                return true;
            }
        }
        return false;
    }
}