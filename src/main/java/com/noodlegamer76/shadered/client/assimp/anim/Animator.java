package com.noodlegamer76.shadered.client.assimp.anim;

import com.noodlegamer76.shadered.client.assimp.McModel;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Animator {
    private final Node root;
    private final Map<String, Integer> boneMap;

    private final Matrix4f[] boneOffsets;
    private final Matrix4f[] finalMatrices;

    private final Matrix4f globalInverse = new Matrix4f().identity();

    private final List<ActiveAnimation> activeAnimations = new ArrayList<>();

    public Animator(Node root, Map<String, Integer> boneMap, Matrix4f[] boneOffsets, int maxBones) {
        this.root = root;
        this.boneMap = boneMap;
        this.boneOffsets = boneOffsets;

        this.finalMatrices = new Matrix4f[maxBones];

        for (int i = 0; i < maxBones; i++) {
            this.finalMatrices[i] = new Matrix4f();
        }

        if (root != null && root.transform != null) {
            this.globalInverse.set(root.transform).invert();
        }
    }

    public Animator(McModel model, int maxBones) {
        Rig rig = model.getRig();

        this.root = rig.root;
        this.boneMap = rig.boneMap;
        this.boneOffsets = rig.boneOffsets;

        this.finalMatrices = new Matrix4f[maxBones];

        for (int i = 0; i < maxBones; i++) {
            this.finalMatrices[i] = new Matrix4f();
        }

        if (rig.root != null && rig.root.transform != null) {
            this.globalInverse.set(rig.root.transform).invert();
        }
    }

    public void play(Animation anim, float weight) {
        activeAnimations.add(new ActiveAnimation(anim, weight));
    }

    public void update(float deltaSeconds) {
        for (ActiveAnimation a : activeAnimations) {
            float ticks = deltaSeconds * a.animation.ticksPerSecond;

            if (a.animation.looping && a.animation.duration > 0f) {
                a.time = (a.time + ticks) % a.animation.duration;
            } else {
                a.time = Math.min(a.time + ticks, a.animation.duration);
            }
        }

        calculate(root, new Matrix4f().identity());
    }

    private void calculate(Node node, Matrix4f parent) {
        Vector3f blendedPos = new Vector3f();
        Quaternionf blendedRot = new Quaternionf(0, 0, 0, 0);
        Vector3f blendedScale = new Vector3f();

        float totalWeight = 0f;

        for (ActiveAnimation a : activeAnimations) {
            BoneTrack track = a.animation.tracks.get(node.name);
            if (track == null) continue;

            Vector3f pos = interpolatePosition(track, a.time);
            Quaternionf rot = interpolateRotation(track, a.time);
            Vector3f scl = interpolateScale(track, a.time);

            float w = a.weight;

            blendedPos.add(new Vector3f(pos).mul(w));
            blendedScale.add(new Vector3f(scl).mul(w));

            Quaternionf temp = new Quaternionf(rot).mul(w);
            blendedRot.add(temp);

            totalWeight += w;
        }

        Matrix4f local = new Matrix4f();

        if (totalWeight > 0f) {
            blendedPos.div(totalWeight);
            blendedScale.div(totalWeight);
            blendedRot.normalize();

            local.translationRotateScale(blendedPos, blendedRot, blendedScale);
        } else {
            local.set(node.transform);
        }

        Matrix4f global = new Matrix4f(parent).mul(local);

        Integer index = boneMap.get(node.name);
        if (index != null && index < finalMatrices.length && index < boneOffsets.length) {
            finalMatrices[index]
                    .set(globalInverse)
                    .mul(global)
                    .mul(boneOffsets[index]);
        }

        for (Node child : node.children) {
            calculate(child, global);
        }
    }

    private Vector3f interpolatePosition(BoneTrack track, float time) {
        if (track.positions.size() == 1) {
            return new Vector3f(track.positions.get(0).value);
        }

        int i = findPositionKey(track, time);
        track.posIndex = i;

        BoneTrack.KeyPosition a = track.positions.get(i);
        BoneTrack.KeyPosition b = track.positions.get(Math.min(i + 1, track.positions.size() - 1));

        float denom = b.time - a.time;
        float t = denom == 0f ? 0f : (time - a.time) / denom;

        return new Vector3f(a.value).lerp(b.value, t);
    }

    private Quaternionf interpolateRotation(BoneTrack track, float time) {
        if (track.rotations.size() == 1) {
            return new Quaternionf(track.rotations.get(0).value);
        }

        int i = findRotationKey(track, time);
        track.rotIndex = i;

        BoneTrack.KeyRotation a = track.rotations.get(i);
        BoneTrack.KeyRotation b = track.rotations.get(Math.min(i + 1, track.rotations.size() - 1));

        float denom = b.time - a.time;
        float t = denom == 0f ? 0f : (time - a.time) / denom;

        return new Quaternionf(a.value).slerp(b.value, t).normalize();
    }

    private Vector3f interpolateScale(BoneTrack track, float time) {
        if (track.scales.size() == 1) {
            return new Vector3f(track.scales.get(0).value);
        }

        int i = findScaleKey(track, time);
        track.scaleIndex = i;

        BoneTrack.KeyScale a = track.scales.get(i);
        BoneTrack.KeyScale b = track.scales.get(Math.min(i + 1, track.scales.size() - 1));

        float denom = b.time - a.time;
        float t = denom == 0f ? 0f : (time - a.time) / denom;

        return new Vector3f(a.value).lerp(b.value, t);
    }

    private int findPositionKey(BoneTrack track, float time) {
        List<BoneTrack.KeyPosition> keys = track.positions;
        if (keys.size() < 2) return 0;

        if (time < keys.get(track.posIndex).time) {
            track.posIndex = 0;
        }

        for (int i = track.posIndex; i < keys.size() - 1; i++) {
            if (time < keys.get(i + 1).time) return i;
        }

        return keys.size() - 2;
    }

    private int findRotationKey(BoneTrack track, float time) {
        List<BoneTrack.KeyRotation> keys = track.rotations;

        if (keys.size() < 2) return 0;

        if (time < keys.get(track.rotIndex).time) {
            track.rotIndex = 0;
        }

        int start = Math.max(0, Math.min(track.rotIndex, keys.size() - 2));

        for (int i = start; i < keys.size() - 1; i++) {
            if (time < keys.get(i + 1).time) return i;
        }

        return keys.size() - 2;
    }

    private int findScaleKey(BoneTrack track, float time) {
        List<BoneTrack.KeyScale> keys = track.scales;

        if (keys.size() < 2) return 0;

        if (time < keys.get(track.scaleIndex).time) {
            track.scaleIndex = 0;
        }

        int start = Math.max(0, Math.min(track.scaleIndex, keys.size() - 2));

        for (int i = start; i < keys.size() - 1; i++) {
            if (time < keys.get(i + 1).time) return i;
        }

        return keys.size() - 2;
    }

    public Matrix4f[] getFinalMatrices() {
        return finalMatrices;
    }

    private static class ActiveAnimation {
        Animation animation;
        float time;
        float weight;

        ActiveAnimation(Animation animation, float weight) {
            this.animation = animation;
            this.weight = weight;
            this.time = 0f;
        }
    }
}