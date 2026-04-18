package com.noodlegamer76.shadered.client.assimp.anim;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Bone {
    public final String name;
    public final int index;

    public final Matrix4f offsetMatrix;

    public Bone parent;
    public final List<Bone> children = new ArrayList<>();

    public Bone(String name, int index, Matrix4f offsetMatrix) {
        this.name = name;
        this.index = index;
        this.offsetMatrix = offsetMatrix;
    }
}