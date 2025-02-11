package com.noodlegamer76.shadered.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.RenderCubeAroundPlayer;
import com.noodlegamer76.shadered.client.util.lighting.Light;
import com.noodlegamer76.shadered.client.util.lighting.PointLight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL44;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, value = Dist.CLIENT)
public class RenderEventsForLights {
    public static ArrayList<Light> lights = new ArrayList<>();

    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {

            //lights.add(new PointLight()
            //                .setColor(new Vector3f(1, 1, 1))
            //                .setPosition(Minecraft.getInstance().player.position().toVector3f()));

            //Render Point Lights
            for (Light light: lights) {
                if (light.getSubtract()) {
                    light.render(event.getPoseStack(), RegisterShadersEvent.subtractPointLight);
                }
                else {
                    light.render(event.getPoseStack(), RegisterShadersEvent.pointLight);
                }
            }
            lights.clear();




            //RegisterShadersEvent.areaFog.setSampler("Depth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
//
            //RenderSystem.enableBlend();
            //RenderSystem.disableDepthTest();
//
            //float scale = 100000;
//
            //if (RegisterShadersEvent.areaFog.getUniform("CameraPos") != null) {
            //    Vector3f cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().toVector3f();
            //    RegisterShadersEvent.areaFog.getUniform("CameraPos").set(cameraPos.x % scale, cameraPos.y % scale, cameraPos.z % scale);
            //}
//
//
            //Matrix4f matrix4f2 = event.getPoseStack().last().pose();
            //if (RegisterShadersEvent.areaFog.getUniform("ModelView2") != null) {
            //    RegisterShadersEvent.areaFog.getUniform("ModelView2").set(matrix4f2);
            //}
//
            //if (RegisterShadersEvent.areaFog.getUniform("FogPos") != null) {
            //    Vector3f fogPos = new Vector3f(0 % scale, 64 % scale, 0 % scale);
            //    RegisterShadersEvent.areaFog.getUniform("FogPos").set(fogPos.x, fogPos.y, fogPos.z);
            //}
//
            //RenderSystem.setShader(() -> RegisterShadersEvent.areaFog);
            //RenderCubeAroundPlayer.renderCubeWithShader(new PoseStack());
            //RenderSystem.disableBlend();
            //RenderSystem.enableDepthTest();
//
            //GL44.glMemoryBarrier(GL44.GL_FRAMEBUFFER_BARRIER_BIT);
        }
    }
}
