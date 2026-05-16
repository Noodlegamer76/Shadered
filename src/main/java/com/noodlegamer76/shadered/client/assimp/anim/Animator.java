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

    public List<ActiveAnimation> getActiveAnimations() {
        return List.copyOf(activeAnimations);
    }

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

    public Animator(McModel model) {
        Rig rig = model.getRig();
        int maxBones = rig.boneMap.size();

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

        if (root != null) {
            calculate(root, new Matrix4f().identity());
        }
    }

    private void calculate(Node node, Matrix4f parent) {
        Vector3f basePos = new Vector3f();
        Quaternionf baseRot = new Quaternionf().identity();
        Vector3f baseScale = new Vector3f(1f, 1f, 1f);

        if (node.transform != null) {
            node.transform.getTranslation(basePos);
            node.transform.getUnnormalizedRotation(baseRot);
            node.transform.getScale(baseScale);
        }

        Vector3f blendedPos = new Vector3f();
        Vector3f blendedScale = new Vector3f();
        Quaternionf blendedRot = new Quaternionf();
        Quaternionf rotReference = null;

        float posWeight = 0f;
        float scaleWeight = 0f;
        float rotWeight = 0f;

        for (ActiveAnimation a : activeAnimations) {
            if (a.weight <= 0f) {
                continue;
            }

            BoneTrack track = a.animation.tracks.get(node.name);
            if (track == null) {
                continue;
            }

            float w = a.weight;

            if (!track.positions.isEmpty()) {
                Vector3f pos = interpolatePosition(track, a.time);
                blendedPos.x += pos.x * w;
                blendedPos.y += pos.y * w;
                blendedPos.z += pos.z * w;
                posWeight += w;
            }

            if (!track.rotations.isEmpty()) {
                Quaternionf rot = interpolateRotation(track, a.time);

                if (rotReference == null) {
                    rotReference = new Quaternionf(rot);
                } else if (rotReference.x * rot.x + rotReference.y * rot.y + rotReference.z * rot.z + rotReference.w * rot.w < 0f) {
                    rot.x = -rot.x;
                    rot.y = -rot.y;
                    rot.z = -rot.z;
                    rot.w = -rot.w;
                }

                blendedRot.x += rot.x * w;
                blendedRot.y += rot.y * w;
                blendedRot.z += rot.z * w;
                blendedRot.w += rot.w * w;
                rotWeight += w;
            }

            if (!track.scales.isEmpty()) {
                Vector3f scl = interpolateScale(track, a.time);
                blendedScale.x += scl.x * w;
                blendedScale.y += scl.y * w;
                blendedScale.z += scl.z * w;
                scaleWeight += w;
            }
        }

        Vector3f finalPos = posWeight > 0f ? blendedPos.div(posWeight) : basePos;
        Quaternionf finalRot = rotWeight > 0f ? blendedRot.normalize() : baseRot;
        Vector3f finalScale = scaleWeight > 0f ? blendedScale.div(scaleWeight) : baseScale;

        Matrix4f local = new Matrix4f().translationRotateScale(finalPos, finalRot, finalScale);
        Matrix4f global = new Matrix4f(parent).mul(local);

        Integer index = boneMap.get(node.name);
        if (index != null && index >= 0 && index < finalMatrices.length && index < boneOffsets.length) {
            finalMatrices[index].set(globalInverse).mul(global).mul(boneOffsets[index]);
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
        BoneTrack.KeyScale a = track.scales.get(i);
        BoneTrack.KeyScale b = track.scales.get(Math.min(i + 1, track.scales.size() - 1));

        float denom = b.time - a.time;
        float t = denom == 0f ? 0f : (time - a.time) / denom;

        return new Vector3f(a.value).lerp(b.value, t);
    }

    private int findPositionKey(BoneTrack track, float time) {
        List<BoneTrack.KeyPosition> keys = track.positions;

        if (keys.size() < 2) {
            return 0;
        }

        int low = 0;
        int high = keys.size() - 2;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            float nextTime = keys.get(mid + 1).time;

            if (time < nextTime) {
                if (mid == 0 || time >= keys.get(mid).time) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return keys.size() - 2;
    }

    private int findRotationKey(BoneTrack track, float time) {
        List<BoneTrack.KeyRotation> keys = track.rotations;

        if (keys.size() < 2) {
            return 0;
        }

        int low = 0;
        int high = keys.size() - 2;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            float nextTime = keys.get(mid + 1).time;

            if (time < nextTime) {
                if (mid == 0 || time >= keys.get(mid).time) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return keys.size() - 2;
    }

    private int findScaleKey(BoneTrack track, float time) {
        List<BoneTrack.KeyScale> keys = track.scales;

        if (keys.size() < 2) {
            return 0;
        }

        int low = 0;
        int high = keys.size() - 2;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            float nextTime = keys.get(mid + 1).time;

            if (time < nextTime) {
                if (mid == 0 || time >= keys.get(mid).time) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return keys.size() - 2;
    }

    public Matrix4f[] getFinalMatrices() {
        return finalMatrices;
    }

    public static class ActiveAnimation {
        public final Animation animation;
        float time;
        float weight;

        ActiveAnimation(Animation animation, float weight) {
            this.animation = animation;
            this.weight = weight;
            this.time = 0f;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }
    }
}