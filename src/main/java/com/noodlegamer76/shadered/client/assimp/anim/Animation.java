package com.noodlegamer76.shadered.client.assimp.anim;

import java.util.HashMap;
import java.util.Map;

public class Animation {
    public String name = "unknown";

    public float duration;
    public float ticksPerSecond;

    public final Map<String, BoneTrack> tracks = new HashMap<>();

    public boolean looping = true;
    public int frameCount = 0;
}