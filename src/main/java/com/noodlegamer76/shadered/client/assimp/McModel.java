package com.noodlegamer76.shadered.client.assimp;

import com.noodlegamer76.shadered.client.assimp.anim.Animation;
import com.noodlegamer76.shadered.client.assimp.anim.Bone;
import com.noodlegamer76.shadered.client.assimp.anim.Rig;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.*;

public class McModel {
    private final ResourceLocation resourceLocation;
    private final List<AssimpModel> models = new ArrayList<>();
    private final Map<Integer, ResourceLocation> embeddedTextures = new HashMap<>();
    private final Map<String, Animation> animations = new HashMap<>();
    private final Map<String, Bone> bones = new HashMap<>();
    private final Matrix4f sceneRootTransform = new Matrix4f().identity();
    private Rig rig;

    public McModel(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public List<AssimpModel> getModels() {
        return models;
    }

    public Map<Integer, ResourceLocation> getEmbeddedTextures() {
        return embeddedTextures;
    }

    public Map<String, Animation> getAnimations() {
        return animations;
    }

    public Map<String, Bone> getBones() {
        return bones;
    }

    public Matrix4f getSceneRootTransform() {
        return sceneRootTransform;
    }

    public void addModel(AssimpModel model) {
        models.add(model);
    }

    public void addEmbeddedTexture(int index, ResourceLocation location) {
        embeddedTextures.put(index, location);
    }

    @Nullable
    public ResourceLocation getEmbeddedTexture(int index) {
        return embeddedTextures.get(index);
    }

    public void addAnimation(String name, Animation animation) {
        animations.put(name, animation);
    }

    public void addBone(Bone bone) {
        bones.put(bone.name, bone);
    }

    public Rig getRig() {
        return rig;
    }

    public void setRig(Rig rig) {
        this.rig = rig;
    }
}