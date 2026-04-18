package com.noodlegamer76.shadered.client.assimp.anim;

import org.joml.Matrix4f;

import java.util.Map;

public class Rig {
    public Node root;
    public Map<String, Integer> boneMap;
    public Matrix4f[] boneOffsets;
}