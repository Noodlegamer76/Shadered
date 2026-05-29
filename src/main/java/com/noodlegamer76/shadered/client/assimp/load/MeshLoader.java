package com.noodlegamer76.shadered.client.assimp.load;

import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.shadered.client.util.ModVertexFormat;
import com.noodlegamer76.shadered.mixin.BufferBuilderMixin;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public class MeshLoader {
    public static VertexBuffer uploadMesh(AIMesh aiMesh, Map<String, Integer> boneMap) {
        VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.TRIANGLES, ModVertexFormat.PBR);

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
                builder.addVertex(pos.x(), pos.y(), pos.z());

                if (texCoords != null) {
                    AIVector3D uv = texCoords.get(index);
                    builder.setUv(uv.x(), uv.y());
                } else {
                    builder.setUv(0, 0);
                }

                if (colors != null) {
                    AIColor4D col = colors.get(index);
                    builder.setColor(col.r(), col.g(), col.b(), col.a());
                } else {
                    builder.setColor(1f, 1f, 1f, 1f);
                }

                if (normals != null) {
                    AIVector3D normal = normals.get(index);
                    builder.setNormal(normal.x(), normal.y(), normal.z());
                }

                setVec4(builder, ModVertexFormat.ELEMENT_TANGENT,
                        new float[]{1, 0, 0, 1}
                );

                setVec4(builder, ModVertexFormat.ELEMENT_INDICES,
                        new float[] {boneIds[index][0], boneIds[index][1], boneIds[index][2], boneIds[index][3]}
                );

                setVec4(builder, ModVertexFormat.ELEMENT_WEIGHTS,
                        new float[] {boneWeights[index][0], boneWeights[index][1], boneWeights[index][2], boneWeights[index][3]}
                );
            }
        }

        MeshData renderedBuffer = builder.build();

        vbo.bind();
        vbo.upload(renderedBuffer);
        VertexBuffer.unbind();

        return vbo;
    }

    private static void setUv(BufferBuilder bb, VertexFormatElement elem, Vector2f uv) {
        long ptr = ((BufferBuilderMixin) bb).shadered$beginElement(elem);
        if (ptr != -1L) {
            MemoryUtil.memPutFloat(ptr, uv.x);
            MemoryUtil.memPutFloat(ptr + 4L, uv.y);
        }
    }

    private static void setVec4(BufferBuilder bb, VertexFormatElement elem, float[] arr) {
        long ptr = ((BufferBuilderMixin) bb).shadered$beginElement(elem);
        if (ptr != -1L) {
            for (int i = 0; i < 4; i++) {
                MemoryUtil.memPutFloat(ptr + (i * 4L), arr[i]);
            }
        }
    }

    private static void setIVec4(BufferBuilder bb, VertexFormatElement elem, int[] arr) {
        long ptr = ((BufferBuilderMixin) bb).shadered$beginElement(elem);
        if (ptr != -1L) {
            for (int i = 0; i < 4; i++) {
                MemoryUtil.memPutInt(ptr + (i * 4L), arr[i]);
            }
        }
    }
}
