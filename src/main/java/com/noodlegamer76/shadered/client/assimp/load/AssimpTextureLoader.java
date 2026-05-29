package com.noodlegamer76.shadered.client.assimp.load;

import com.mojang.blaze3d.platform.NativeImage;
import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.assimp.AITexture;
import org.lwjgl.assimp.AITexel;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class AssimpTextureLoader {
    public static ResourceLocation uploadEmbeddedTexture(String modelName, int index, AITexture aiTexture) {
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "generated/" + modelName + "/tex_" + index);

        try {
            NativeImage image;

            if (aiTexture.mHeight() == 0) {
                ByteBuffer compressedData = aiTexture.pcDataCompressed();

                try (MemoryStack stack = MemoryStack.stackPush()) {
                    IntBuffer width = stack.mallocInt(1);
                    IntBuffer height = stack.mallocInt(1);
                    IntBuffer channels = stack.mallocInt(1);

                    ByteBuffer decodedPixels = STBImage.stbi_load_from_memory(compressedData, width, height, channels, 4);
                    if (decodedPixels == null) {
                        throw new IOException("STB Image failed to decode compressed embedded texture: " + STBImage.stbi_failure_reason());
                    }

                    image = new NativeImage(width.get(0), height.get(0), false);

                    for (int y = 0; y < image.getHeight(); y++) {
                        for (int x = 0; x < image.getWidth(); x++) {
                            int idx = (y * image.getWidth() + x) * 4;
                            int r = decodedPixels.get(idx) & 0xFF;
                            int g = decodedPixels.get(idx + 1) & 0xFF;
                            int b = decodedPixels.get(idx + 2) & 0xFF;
                            int a = decodedPixels.get(idx + 3) & 0xFF;

                            int abgr = (a << 24) | (b << 16) | (g << 8) | r;
                            image.setPixelRGBA(x, y, abgr);
                        }
                    }

                    STBImage.stbi_image_free(decodedPixels);
                }
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