package com.noodlegamer76.shadered.client.assimp.load;

import com.mojang.blaze3d.platform.NativeImage;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.assimp.AITexture;
import org.lwjgl.assimp.AITexel;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AssimpTextureLoader {
    public static ResourceLocation uploadEmbeddedTexture(String modelName, int index, AITexture aiTexture) {
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "generated/" + modelName + "/tex_" + index);

        try {
            NativeImage image;

            if (aiTexture.mHeight() == 0) {
                ByteBuffer data = aiTexture.pcDataCompressed();
                image = NativeImage.read(data);
            } else {
                int width = aiTexture.mWidth();
                int height = aiTexture.mHeight();
                image = new NativeImage(width, height, false);

                AITexel.Buffer texels = aiTexture.pcData();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        AITexel texel = texels.get(y * width + x);
                        int abgr = (texel.a() << 24) | (texel.b() << 16) | (texel.g() << 8) | texel.r();
                        image.setPixelRGBA(x, y, abgr);
                    }
                }
            }

            DynamicTexture dynamicTexture = new DynamicTexture(image);
            Minecraft.getInstance().getTextureManager().register(loc, dynamicTexture);
            return loc;

        } catch (IOException e) {
            throw new RuntimeException("Failed to decode Assimp texture: " + loc, e);
        }
    }
}