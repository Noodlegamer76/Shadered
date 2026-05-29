package com.noodlegamer76.shadered.core;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class EngineMath {

    private EngineMath() {
    }

    public static Vector3f lerp(Vector3f a, Vector3f b, float t, Vector3f dest) {
        dest.x = a.x + (b.x - a.x) * t;
        dest.y = a.y + (b.y - a.y) * t;
        dest.z = a.z + (b.z - a.z) * t;
        return dest;
    }

    public static Vector3f lerp(Vector3f a, Vector3f b, float t) {
        return lerp(a, b, t, new Vector3f());
    }

    public static Quaternionf nlerp(Quaternionf a, Quaternionf b, float t, Quaternionf dest) {
        if (a.dot(b) < 0.0f) {
            dest.set(
                    a.x + (-b.x - a.x) * t,
                    a.y + (-b.y - a.y) * t,
                    a.z + (-b.z - a.z) * t,
                    a.w + (-b.w - a.w) * t
            );
        } else {
            dest.set(
                    a.x + (b.x - a.x) * t,
                    a.y + (b.y - a.y) * t,
                    a.z + (b.z - a.z) * t,
                    a.w + (b.w - a.w) * t
            );
        }

        return dest.normalize();
    }

    public static Quaternionf nlerp(Quaternionf a, Quaternionf b, float t) {
        return nlerp(a, b, t, new Quaternionf());
    }

    public static Quaternionf slerp(Quaternionf a, Quaternionf b, float t, Quaternionf dest) {
        return dest.set(a).slerp(b, t);
    }

    public static Quaternionf slerp(Quaternionf a, Quaternionf b, float t) {
        return slerp(a, b, t, new Quaternionf());
    }
}
