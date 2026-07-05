package com.noodlegamer76.shadered.client.util.shader.lights;

import org.joml.Vector3f;

public class Light {
    public final Vector3f pos = new Vector3f();
    public final Vector3f color = new Vector3f(1, 1, 1);
    public float radius = 10f;

    public Light(float x, float y, float z, float r, float g, float b, float radius) {
        this.pos.set(x, y, z);
        this.color.set(r, g, b);
        this.radius = radius;
    }
}