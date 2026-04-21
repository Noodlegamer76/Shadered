package com.noodlegamer76.shadered.client.util.shader.lights;

import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.shadered.event.RegisterShaders;
import me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.common.ForgeHooks;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LightUploader {
    private static final int MAX = 32;
    private static final List<Light> lights = new ArrayList<>();
    private static final List<Light> lightQueue = new ArrayList<>();

    public static void addLight(Light light) {
        lightQueue.add(light);
    }

    public static void clearLights() {
        lights.clear();
    }

    public static List<Light> getLights() {
        return Collections.unmodifiableList(lights);
    }

    public static void uploadToAll() {
        lights.clear();
        lights.addAll(lightQueue);
        lightQueue.clear();

        List<ShaderInstance> lightShaders = List.of(
                GameRenderer.getRendertypeEntityCutoutShader(),
                GameRenderer.getRendertypeEntityTranslucentShader(),
                GameRenderer.getRendertypeEntitySolidShader(),
                GameRenderer.getParticleShader(),
                GameRenderer.getRendertypeEntityCutoutNoCullShader(),
                GameRenderer.getRendertypeCutoutMippedShader(),
                GameRenderer.getRendertypeSolidShader(),
                GameRenderer.getRendertypeLeashShader(),
                GameRenderer.getRendertypeEntitySmoothCutoutShader(),
                GameRenderer.getRendertypeEntityTranslucentCullShader(),
                GameRenderer.getRendertypeItemEntityTranslucentCullShader(),
                GameRenderer.getRendertypeEntityCutoutNoCullZOffsetShader(),
                GameRenderer.getRendertypeCutoutShader(),
                ForgeHooksClient.ClientEvents.getEntityTranslucentUnlitShader(),
                GameRenderer.getPositionTexColorNormalShader(),
                GameRenderer.getRendertypeArmorCutoutNoCullShader(),
                GameRenderer.getRendertypeEntityDecalShader(),
                GameRenderer.getRendertypeEntityNoOutlineShader(),
                GameRenderer.getRendertypeEntityShadowShader(),
                GameRenderer.getRendertypeEntityTranslucentEmissiveShader(),
                GameRenderer.getRendertypeOutlineShader(),
                GameRenderer.getRendertypeTranslucentShader(),
                GameRenderer.getRendertypeTripwireShader(),
                RegisterShaders.getPbr()
        );

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 cam = camera.getPosition();

        float Y = (float) Math.toRadians(camera.getYRot() + 180f);
        float P = (float) Math.toRadians(camera.getXRot());
        float cosY = (float) Math.cos(Y), sinY = (float) Math.sin(Y);
        float cosP = (float) Math.cos(P), sinP = (float) Math.sin(P);

        lights.sort((a, b) -> {
            float da = a.pos.distanceSquared((float) cam.x, (float) cam.y, (float) cam.z);
            float db = b.pos.distanceSquared((float) cam.x, (float) cam.y, (float) cam.z);
            float ia = a.radius * a.radius / (da + 0.0001f);
            float ib = b.radius * b.radius / (db + 0.0001f);
            return Float.compare(ib, ia);
        });

        int count = Math.min(lights.size(), MAX);

        for (ShaderInstance shader : lightShaders) {
            if (shader == null) continue;

            shader.apply();
            int program = shader.getId();

            int countLoc = GL20.glGetUniformLocation(program, "u_LightCount");
            if (countLoc != -1) GL20.glUniform1i(countLoc, count);

            for (int i = 0; i < count; i++) {
                Light light = lights.get(i);

                float rx = (float)(light.pos.x - cam.x);
                float ry = (float)(light.pos.y - cam.y);
                float rz = (float)(light.pos.z - cam.z);

                float vx =  cosY * rx + sinY * rz;
                float vy =  sinP * sinY * rx + cosP * ry - sinP * cosY * rz;
                float vz = -cosP * sinY * rx + sinP * ry + cosP * cosY * rz;

                int posLoc = GL20.glGetUniformLocation(program, "u_LightPos[" + i + "]");
                if (posLoc != -1) GL20.glUniform3f(posLoc, vx, vy, vz);

                int colorLoc = GL20.glGetUniformLocation(program, "u_LightColor[" + i + "]");
                if (colorLoc != -1) GL20.glUniform3f(colorLoc, light.color.x, light.color.y, light.color.z);

                int radiusLoc = GL20.glGetUniformLocation(program, "u_LightRadius[" + i + "]");
                if (radiusLoc != -1) GL20.glUniform1f(radiusLoc, light.radius);
            }
        }
    }

    public static void upload(int program) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 cam = camera.getPosition();

        lights.sort((a, b) -> {
            float da = a.pos.distanceSquared((float) cam.x, (float) cam.y, (float) cam.z);
            float db = b.pos.distanceSquared((float) cam.x, (float) cam.y, (float) cam.z);

            float ia = a.radius * a.radius / (da + 0.0001f);
            float ib = b.radius * b.radius / (db + 0.0001f);

            return Float.compare(ib, ia);
        });

        int count = Math.min(lights.size(), MAX);

        int countLoc = GL20.glGetUniformLocation(program, "u_LightCount");
        if (countLoc != -1) GL20.glUniform1i(countLoc, count);

        for (int i = 0; i < count; i++) {
            Light light = lights.get(i);

            float rx = (float)(light.pos.x - cam.x);
            float ry = (float)(light.pos.y - cam.y);
            float rz = (float)(light.pos.z - cam.z);

            int posLoc = GL20.glGetUniformLocation(program, "u_LightPos[" + i + "]");
            if (posLoc != -1) GL20.glUniform3f(posLoc, rx, ry, rz);

            int colorLoc = GL20.glGetUniformLocation(program, "u_LightColor[" + i + "]");
            if (colorLoc != -1) GL20.glUniform3f(colorLoc, light.color.x, light.color.y, light.color.z);

            int radiusLoc = GL20.glGetUniformLocation(program, "u_LightRadius[" + i + "]");
            if (radiusLoc != -1) GL20.glUniform1f(radiusLoc, light.radius);
        }
    }
}