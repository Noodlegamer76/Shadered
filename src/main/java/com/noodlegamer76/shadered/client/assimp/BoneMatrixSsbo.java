package com.noodlegamer76.shadered.client.assimp;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

public class BoneMatrixSsbo {
    public static final int MAX_BONES = 1024 * 16;
    private static final Matrix4f IDENTITY = new Matrix4f();

    public final int ssbo;
    private final FloatBuffer buffer;

    public BoneMatrixSsbo() {
        ssbo = GL15.glGenBuffers();
        buffer = BufferUtils.createFloatBuffer(MAX_BONES * 16);

        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, (long) MAX_BONES * 16 * Float.BYTES, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);

        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, 0, ssbo);
    }

    public void upload(List<Matrix4f> matrices) {
        buffer.clear();
        float[] tmp = new float[16];

        for (int i = 0; i < MAX_BONES; i++) {
            if (i < matrices.size()) {
                matrices.get(i).get(tmp);
            } else {
                IDENTITY.get(tmp);
            }
            buffer.put(tmp);
        }

        buffer.flip();

        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
        GL15.glBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
    }
}