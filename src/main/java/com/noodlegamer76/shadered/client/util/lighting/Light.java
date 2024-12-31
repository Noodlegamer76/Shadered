package com.noodlegamer76.shadered.client.util.lighting;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.shadered.event.RegisterShadersEvent;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL44;

public class Light {
    private Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
    private float alpha = 1.0f;
    private Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);

    public Light setColor(Vector3f color) {
        this.color = color;
        return this;
    }

    public Light setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public Light setPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public void render(PoseStack poseStack, ShaderInstance lightShader) {

        RenderSystem.setShader(() -> lightShader);
        GlStateManager._glUseProgram(lightShader.getId());
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();

        setUniforms(lightShader, poseStack);

        GlStateManager.glActiveTexture(GL44.GL_TEXTURE0);
        GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        GlStateManager._glUniform1i(GL44.glGetUniformLocation(lightShader.getId(), "Depth"), 0);

        RenderCubeAroundPlayer.renderCubeWithShader(poseStack);

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();

        //to render this before the depth buffer is cleared
        GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
    }

    protected void setUniforms(ShaderInstance light, PoseStack poseStack) {

        Vector3f cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().toVector3f();
        Matrix4f matrix4f2 = poseStack.last().pose();

        if (light.getUniform("CameraPos") != null) {
            light.getUniform("CameraPos").set(cameraPos);
        }
        if (light.getUniform("ModelView2") != null) {
            light.getUniform("ModelView2").set(matrix4f2);
        }
        if (light.getUniform("LightPos") != null) {
            light.getUniform("LightPos").set(position);
        }
        if (light.getUniform("LightColor") != null) {
            light.getUniform("LightColor").set(color.x, color.y, color.z, alpha);
        }
    }
}
