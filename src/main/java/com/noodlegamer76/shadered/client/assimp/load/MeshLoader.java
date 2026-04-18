package com.noodlegamer76.shadered.client.assimp.load;

import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.client.util.ModVertexFormat;
import com.noodlegamer76.shadered.mixin.BufferBuilderAccessor;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public class MeshLoader {
    public static VertexBuffer uploadMesh(AIMesh aiMesh, Map<String, Integer> boneMap) {
        VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.TRIANGLES, ModVertexFormat.PBR);

        int vertexCount = aiMesh.mNumVertices();
        int MAX_BONES_PER_VERTEX = 4;

        int[][] boneIds = new int[vertexCount][MAX_BONES_PER_VERTEX];
        float[][] boneWeights = new float[vertexCount][MAX_BONES_PER_VERTEX];

        AIVector3D.Buffer vertices = aiMesh.mVertices();
        AIVector3D.Buffer normals = aiMesh.mNormals();
        AIVector3D.Buffer texCoords = aiMesh.mTextureCoords(0);
        AIColor4D.Buffer colors = aiMesh.mColors(0);

        int numBones = aiMesh.mNumBones();
        PointerBuffer bones = aiMesh.mBones();

        for (int boneIndex = 0; boneIndex < numBones; boneIndex++) {

            AIBone bone = AIBone.create(bones.get(boneIndex));
            String name = bone.mName().dataString();

            Integer mappedId = boneMap.get(name);
            if (mappedId == null) continue;

            AIVertexWeight.Buffer weights = bone.mWeights();

            for (int w = 0; w < bone.mNumWeights(); w++) {

                AIVertexWeight weight = weights.get(w);

                int vertexId = weight.mVertexId();
                float value = weight.mWeight();

                for (int slot = 0; slot < MAX_BONES_PER_VERTEX; slot++) {

                    if (boneWeights[vertexId][slot] == 0.0f) {
                        boneIds[vertexId][slot] = mappedId;
                        boneWeights[vertexId][slot] = value;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < vertexCount; i++) {

            float sum = 0.0f;

            for (int j = 0; j < MAX_BONES_PER_VERTEX; j++) {
                sum += boneWeights[i][j];
            }

            if (sum > 0.0f) {
                for (int j = 0; j < MAX_BONES_PER_VERTEX; j++) {
                    boneWeights[i][j] /= sum;
                }
            }
        }

        AIFace.Buffer faces = aiMesh.mFaces();

        for (int i = 0; i < aiMesh.mNumFaces(); i++) {

            AIFace face = faces.get(i);
            IntBuffer indices = face.mIndices();

            for (int j = 0; j < 3; j++) {

                int index = indices.get(j);

                AIVector3D pos = vertices.get(index);
                builder.vertex(pos.x(), pos.y(), pos.z());

                if (texCoords != null) {
                    AIVector3D uv = texCoords.get(index);
                    builder.uv(uv.x(), uv.y());
                } else {
                    builder.uv(0, 0);
                }

                if (colors != null) {
                    AIColor4D col = colors.get(index);
                    builder.color(col.r(), col.g(), col.b(), col.a());
                } else {
                    builder.color(1f, 1f, 1f, 1f);
                }

                if (normals != null) {
                    AIVector3D normal = normals.get(index);
                    builder.normal(normal.x(), normal.y(), normal.z());
                }

                builder.putFloat(0, 1f);
                builder.putFloat(4, 0f);
                builder.putFloat(8, 0f);
                builder.putFloat(12, 1f);

                builder.nextElement();

                builder.putFloat(0, boneIds[index][0]);
                builder.putFloat(4, boneIds[index][1]);
                builder.putFloat(8, boneIds[index][2]);
                builder.putFloat(12, boneIds[index][3]);

                builder.nextElement();

                builder.putFloat(0, boneWeights[index][0]);
                builder.putFloat(4, boneWeights[index][1]);
                builder.putFloat(8, boneWeights[index][2]);
                builder.putFloat(12, boneWeights[index][3]);

                builder.nextElement();

                builder.endVertex();
            }
        }

        BufferBuilder.RenderedBuffer renderedBuffer = builder.end();

        vbo.bind();
        vbo.upload(renderedBuffer);
        VertexBuffer.unbind();

        return vbo;
    }
}
