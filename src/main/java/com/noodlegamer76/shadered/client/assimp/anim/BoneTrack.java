package com.noodlegamer76.shadered.client.assimp.anim;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BoneTrack {

    public static class KeyPosition {
        public float time;
        public Vector3f value;
    }

    public static class KeyRotation {
        public float time;
        public Quaternionf value;
    }

    public static class KeyScale {
        public float time;
        public Vector3f value;
    }

    public final List<KeyPosition> positions = new ArrayList<>();
    public final List<KeyRotation> rotations = new ArrayList<>();
    public final List<KeyScale> scales = new ArrayList<>();

    public int posIndex = 0;
    public int rotIndex = 0;
    public int scaleIndex = 0;
}