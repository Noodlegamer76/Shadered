package com.noodlegamer76.shadered.client.assimp.load;

import com.mojang.blaze3d.vertex.VertexBuffer;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.assimp.AssimpMaterial;
import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.client.assimp.McModel;
import com.noodlegamer76.shadered.client.assimp.anim.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.FilenameUtils;
import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssimpLoader {

    public static void load() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        Map<ResourceLocation, AIScene> loadedScenes = new HashMap<>();
        String folder = "models/complex";

        Map<ResourceLocation, Resource> locations =
                resourceManager.listResources(folder, path -> true);

        for (Map.Entry<ResourceLocation, Resource> entry : locations.entrySet()) {

            ResourceLocation location = entry.getKey();
            AIScene scene = null;

            try (InputStream stream = entry.getValue().open()) {

                byte[] bytes = stream.readAllBytes();

                ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
                buffer.put(bytes).flip();

                int flags =
                        Assimp.aiProcess_Triangulate |
                                Assimp.aiProcess_FlipUVs |
                                Assimp.aiProcess_CalcTangentSpace |
                                Assimp.aiProcess_OptimizeMeshes |
                                Assimp.aiProcess_GlobalScale |
                                Assimp.aiProcess_SortByPType |
                                Assimp.aiProcess_ImproveCacheLocality;

                String hint = FilenameUtils.getExtension(location.getPath());

                scene = Assimp.aiImportFileFromMemory(buffer, flags, hint);

                if (scene != null
                        && (scene.mFlags() & Assimp.AI_SCENE_FLAGS_INCOMPLETE) == 0
                        && scene.mRootNode() != null) {

                    loadedScenes.put(location, scene);

                } else {
                    ShaderedMod.LOGGER.error(
                            "Assimp Error loading {}: {}",
                            location,
                            Assimp.aiGetErrorString()
                    );
                }

                MemoryUtil.memFree(buffer);

            } catch (Exception e) {
                ShaderedMod.LOGGER.error("Failed to prepare model: {}", location, e);
                continue;
            }
        }

        AssimpModels.clear();

        for (var entry : loadedScenes.entrySet()) {

            ResourceLocation location = entry.getKey();
            AIScene scene = entry.getValue();

            try {
                McModel model = new McModel(location);

                String modelName = FilenameUtils.getBaseName(location.getPath());

                model.setRig(RigLoader.load(scene));

                int numTextures = scene.mNumTextures();
                if (numTextures > 0) {
                    PointerBuffer texturePointers = scene.mTextures();

                    for (int i = 0; i < numTextures; i++) {
                        AITexture aiTexture =
                                AITexture.create(texturePointers.get(i));

                        ResourceLocation texLoc =
                                AssimpTextureLoader.uploadEmbeddedTexture(
                                        modelName,
                                        i,
                                        aiTexture
                                );

                        model.addEmbeddedTexture(i, texLoc);
                    }
                }

                Map<Integer, AssimpMaterial> materials =
                        MaterialLoader.loadMaterials(scene, model);

                int numMeshes = scene.mNumMeshes();
                PointerBuffer meshPointers = scene.mMeshes();

                for (int i = 0; i < numMeshes; i++) {

                    AIMesh aiMesh = AIMesh.create(meshPointers.get(i));

                    VertexBuffer vbo =
                            MeshLoader.uploadMesh(aiMesh, model.getRig().boneMap);

                    AssimpMaterial material =
                            materials.get(aiMesh.mMaterialIndex());

                    AssimpModel meshPart =
                            new AssimpModel(vbo, material);

                    List<AssimpModel.MeshInstanceTransform> transforms =
                            NodeLoader.loadNodeHierarchy(scene).getOrDefault(i, List.of(
                                    new AssimpModel.MeshInstanceTransform(
                                            new Matrix4f().identity(),
                                            new Matrix4f().identity()
                                    )
                            ));

                    meshPart.instances.addAll(transforms);

                    model.addModel(meshPart);
                }

                Map<String, Animation> animations =
                        AnimationLoader.load(scene);

                for (var anim : animations.entrySet()) {
                    model.addAnimation(anim.getKey(), anim.getValue());
                }


                AssimpModels.registerModel(location, model);

                ShaderedMod.LOGGER.info(
                        "Successfully loaded and uploaded model: {}",
                        location
                );

            } catch (Exception e) {
                ShaderedMod.LOGGER.error(
                        "Failed to upload model to GPU: {}",
                        location,
                        e
                );
            } finally {
                Assimp.aiReleaseImport(scene);
            }
        }
    }
}