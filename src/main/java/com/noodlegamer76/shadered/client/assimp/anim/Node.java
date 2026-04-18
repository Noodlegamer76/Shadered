package com.noodlegamer76.shadered.client.assimp.anim;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String name;
    public Matrix4f transform;
    public List<Node> children = new ArrayList<>();
}