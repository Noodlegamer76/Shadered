package com.noodlegamer76.shadered.client.renderer.puddle;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.ExtendedShaderInstance;
import com.noodlegamer76.shadered.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PuddleCubeRenderer {
    public static final List<PuddleInfo> puddles = new ArrayList<>();

    public static void renderPuddles(PoseStack poseStack, PuddleInfo puddleInfo) {
        Color color = puddleInfo.getColor();

        RenderSystem.setShader(() -> RegisterShadersEvent.puddleShader);

        ExtendedShaderInstance shader = (ExtendedShaderInstance) RenderSystem.getShader();
        Uniform inverseModelViewMat = shader.getUniform("InverseModelViewMat");

        if (inverseModelViewMat != null) {
            inverseModelViewMat.set(new Matrix4f(poseStack.last().pose()).invert());
        }


        RenderCubeAroundPlayer.renderCubeWithShader(poseStack, color);

    }
}
