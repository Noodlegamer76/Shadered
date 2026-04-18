package com.noodlegamer76.shadered.client.renderer.assimp;

import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.client.assimp.anim.Animator;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class RenderableModel {
    private final List<AssimpModel> model = new ArrayList<>();
    public Matrix4f modelMatrix = new Matrix4f();
    public Animator animator;

    public void add(AssimpModel model) {
        this.model.add(model);
    }

    public List<AssimpModel> getModel() {
        return model;
    }
}
