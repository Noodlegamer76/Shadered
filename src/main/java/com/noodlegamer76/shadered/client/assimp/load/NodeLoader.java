package com.noodlegamer76.shadered.client.assimp.load;

import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.*;

public class NodeLoader {

    public static Map<Integer, List<AssimpModel.MeshInstanceTransform>> loadNodeHierarchy(AIScene scene) {
        Map<Integer, List<AssimpModel.MeshInstanceTransform>> instances = new HashMap<>();
        processNode(scene.mRootNode(), new Matrix4f().identity(), instances);
        return instances;
    }

    private static void processNode(
            AINode node,
            Matrix4f parentTransform,
            Map<Integer, List<AssimpModel.MeshInstanceTransform>> instances
    ) {
        if (node == null) return;

        Matrix4f localTransform = toMatrix(node.mTransformation());
        Matrix4f globalTransform = new Matrix4f(parentTransform).mul(localTransform);

        IntBuffer meshIndices = node.mMeshes();
        int meshCount = node.mNumMeshes();

        for (int i = 0; i < meshCount; i++) {
            int meshIndex = meshIndices.get(i);

            instances
                    .computeIfAbsent(meshIndex, k -> new ArrayList<>())
                    .add(new AssimpModel.MeshInstanceTransform(
                            new Matrix4f(localTransform),
                            new Matrix4f(globalTransform)
                    ));

        }

        PointerBuffer children = node.mChildren();
        int childCount = node.mNumChildren();

        for (int i = 0; i < childCount; i++) {
            processNode(
                    AINode.create(children.get(i)),
                    globalTransform,
                    instances
            );
        }
    }

    private static Matrix4f toMatrix(AIMatrix4x4 m) {
        return new Matrix4f(
                m.a1(), m.b1(), m.c1(), m.d1(),
                m.a2(), m.b2(), m.c2(), m.d2(),
                m.a3(), m.b3(), m.c3(), m.d3(),
                m.a4(), m.b4(), m.c4(), m.d4()
        );
    }
}