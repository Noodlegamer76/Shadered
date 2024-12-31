package com.noodlegamer76.shadered.client.util.lighting;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Vector3f;

public class PointLight extends Light {
    private float constant = 1.0f;
    private float linear = 0.1f;
    private float quadratic = 0.01f;

    public PointLight setConstant(float constant) {
        this.constant = constant;
        return this;
    }

    public PointLight setLinear(float linear) {
        this.linear = linear;
        return this;
    }

    public PointLight setQuadratic(float quadratic) {
        this.quadratic = quadratic;
        return this;
    }

    @Override
    protected void setUniforms(ShaderInstance light, PoseStack poseStack) {
        super.setUniforms(light, poseStack);

        if (light.getUniform("PointVariables") != null) {
            light.getUniform("PointVariables").set(new Vector3f(constant, linear, quadratic));
        }
    }
}
