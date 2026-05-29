package com.noodlegamer76.shadered.client.assimp;

import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssimpModel {
    private final VertexBuffer vertexBuffer;
    public List<MeshInstanceTransform> instances = new ArrayList<>();
    private AssimpMaterial material;

    public AssimpModel(VertexBuffer vertexBuffer, AssimpMaterial material) {
        this.vertexBuffer = vertexBuffer;
        this.material = material;
    }

    public VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public AssimpMaterial getMaterial() {
        return material;
    }

    public record MeshInstanceTransform(Matrix4f local, Matrix4f global) {
    }
}
