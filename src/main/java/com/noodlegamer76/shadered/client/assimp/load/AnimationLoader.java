package com.noodlegamer76.shadered.client.assimp.load;

import com.noodlegamer76.shadered.client.assimp.anim.Animation;
import com.noodlegamer76.shadered.client.assimp.anim.BoneTrack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.HashMap;
import java.util.Map;

public class AnimationLoader {
    public static Map<String, Animation> load(AIScene scene) {

        Map<String, Animation> animations = new HashMap<>();

        PointerBuffer ptr = scene.mAnimations();

        for (int i = 0; i < scene.mNumAnimations(); i++) {

            AIAnimation aiAnim = AIAnimation.create(ptr.get(i));

            Animation anim = new Animation();
            anim.duration = (float) aiAnim.mDuration();
            anim.ticksPerSecond =
                    aiAnim.mTicksPerSecond() != 0
                            ? (float) aiAnim.mTicksPerSecond()
                            : 25.0f;

            anim.name = aiAnim.mName().dataString();
            if (anim.name.isEmpty()) anim.name = "anim_" + i;

            PointerBuffer channels = aiAnim.mChannels();

            for (int j = 0; j < aiAnim.mNumChannels(); j++) {

                AINodeAnim ch = AINodeAnim.create(channels.get(j));
                String name = ch.mNodeName().dataString();

                BoneTrack track = new BoneTrack();

                for (int k = 0; k < ch.mNumPositionKeys(); k++) {
                    var key = ch.mPositionKeys().get(k);
                    var kp = new BoneTrack.KeyPosition();
                    kp.time = (float) key.mTime();
                    kp.value = new Vector3f(
                            key.mValue().x(),
                            key.mValue().y(),
                            key.mValue().z()
                    );
                    track.positions.add(kp);
                }

                for (int k = 0; k < ch.mNumRotationKeys(); k++) {
                    var key = ch.mRotationKeys().get(k);
                    var kr = new BoneTrack.KeyRotation();
                    kr.time = (float) key.mTime();
                    kr.value = new Quaternionf(
                            key.mValue().x(),
                            key.mValue().y(),
                            key.mValue().z(),
                            key.mValue().w()
                    );
                    track.rotations.add(kr);
                }

                for (int k = 0; k < ch.mNumScalingKeys(); k++) {
                    var key = ch.mScalingKeys().get(k);
                    var ks = new BoneTrack.KeyScale();
                    ks.time = (float) key.mTime();
                    ks.value = new Vector3f(
                            key.mValue().x(),
                            key.mValue().y(),
                            key.mValue().z()
                    );
                    track.scales.add(ks);
                }

                anim.tracks.put(name, track);
            }

            animations.put(anim.name, anim);
        }

        return animations;
    }
}