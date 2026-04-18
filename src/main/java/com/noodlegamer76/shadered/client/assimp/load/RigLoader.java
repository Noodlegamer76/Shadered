package com.noodlegamer76.shadered.client.assimp.load;

import com.noodlegamer76.shadered.client.assimp.anim.Node;
import com.noodlegamer76.shadered.client.assimp.anim.Rig;
import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.HashMap;
import java.util.Map;

public class RigLoader {

    public static Rig load(AIScene scene) {

        Rig rig = new Rig();

        rig.root = buildNode(scene.mRootNode());

        Map<String, Integer> boneMap = new HashMap<>();
        Map<String, Matrix4f> offsetMap = new HashMap<>();

        PointerBuffer meshes = scene.mMeshes();

        int boneCounter = 0;

        for (int i = 0; i < scene.mNumMeshes(); i++) {
            AIMesh mesh = AIMesh.create(meshes.get(i));

            PointerBuffer bones = mesh.mBones();

            for (int j = 0; j < mesh.mNumBones(); j++) {
                AIBone aiBone = AIBone.create(bones.get(j));

                String name = aiBone.mName().dataString();

                if (!boneMap.containsKey(name)) {
                    boneMap.put(name, boneCounter++);

                    offsetMap.put(name, toMatrix(aiBone.mOffsetMatrix()));
                }
            }
        }

        Matrix4f[] boneOffsets = new Matrix4f[boneMap.size()];

        for (Map.Entry<String, Integer> entry : boneMap.entrySet()) {
            boneOffsets[entry.getValue()] = offsetMap.get(entry.getKey());
        }

        rig.boneMap = boneMap;
        rig.boneOffsets = boneOffsets;

        return rig;
    }

    private static Node buildNode(AINode aiNode) {

        Node node = new Node();
        node.name = aiNode.mName().dataString();
        node.transform = toMatrix(aiNode.mTransformation());

        PointerBuffer children = aiNode.mChildren();

        for (int i = 0; i < aiNode.mNumChildren(); i++) {
            node.children.add(buildNode(AINode.create(children.get(i))));
        }

        return node;
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