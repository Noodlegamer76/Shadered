package com.noodlegamer76.shadered.client.renderer.assimp;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Axis;
import com.noodlegamer76.shadered.client.assimp.AssimpMaterial;
import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.event.RegisterShaders;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL43;

import java.util.*;

public class AssimpRenderer {
    private static final AssimpRenderer INSTANCE = new AssimpRenderer();
    public static AssimpRenderer getInstance() { return INSTANCE; }

    private final Map<AssimpMaterial, Map<VertexBuffer, List<RenderEntry>>> opaqueEntries = new HashMap<>();
    private final List<RenderEntry> transparentEntries = new ArrayList<>();
    private final BoneMatrixSsbo boneMatrixSsbo = new BoneMatrixSsbo();
    private final Set<RenderableModel> models = new HashSet<>();
    private final Map<RenderableModel, Integer> modelOffsets = new LinkedHashMap<>();
    private int lastModelOffset = 1;

    private AssimpRenderer() {}

    public void addModel(RenderableModel model) {
        models.add(model);

        Vector3f cameraPos = Minecraft.getInstance()
                .gameRenderer.getMainCamera()
                .getPosition()
                .toVector3f();

        for (AssimpModel part : model.getModel()) {
            for (AssimpModel.MeshInstanceTransform inst : part.instances) {

                Matrix4f worldMatrix = new Matrix4f(model.modelMatrix).mul(inst.global());

                if (part.getMaterial().useAlpha()) {
                    Vector3f pos = new Vector3f();
                    worldMatrix.getTranslation(pos);
                    float distSq = pos.distanceSquared(cameraPos);

                    transparentEntries.add(
                            new RenderEntry(part, model, distSq)
                    );
                } else {
                    opaqueEntries
                            .computeIfAbsent(part.getMaterial(), k -> new HashMap<>())
                            .computeIfAbsent(part.getVertexBuffer(), k -> new ArrayList<>())
                            .add(
                                    new RenderEntry(part, model, 0)
                            );
                }
            }
        }
    }

    public void render(float partialTick, int renderTick) {
        PoseStack poseStack = new PoseStack();
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();

        poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        poseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

        List<Matrix4f> allBones = new ArrayList<>();

        modelOffsets.clear();
        int cursor = 0;

        for (RenderableModel model : models) {
            if (model.animator != null) {
                float renderTime = (mc.level.getGameTime() + partialTick) / 20.0f;
                model.animator.update(renderTime);

                Matrix4f[] bones = model.animator.getFinalMatrices();

                modelOffsets.put(model, cursor);

                for (Matrix4f bone : bones) {
                    allBones.add(new Matrix4f(bone));
                }
                cursor += bones.length;
            } else {
                modelOffsets.put(model, -1);
            }
        }

        boneMatrixSsbo.upload(allBones);

        GL43.glMemoryBarrier(GL43.GL_SHADER_STORAGE_BARRIER_BIT);

        RenderSystem.setShader(RegisterShaders::getPbr);

        Uniform viewMat = RegisterShaders.getPbr().getUniform("ViewMat");
        if (viewMat != null) viewMat.set(poseStack.last().pose());

        Uniform cameraPos = RegisterShaders.getPbr().getUniform("CameraPos");
        if (cameraPos != null) {
            cameraPos.set((float) camera.getPosition().x, (float) camera.getPosition().y, (float) camera.getPosition().z);
        }

        Vec3 lightDir = new Vec3(0.35, -1.0, 0.25).normalize();
        Uniform lightDirection = RegisterShaders.getPbr().getUniform("LightDirection");
        if (lightDirection != null) {
            lightDirection.set((float) lightDir.x, (float) lightDir.y, (float) lightDir.z);
        }

        Uniform lightColor = RegisterShaders.getPbr().getUniform("LightColor");
        if (lightColor != null) lightColor.set(2.5f, 2.4f, 2.2f);

        Uniform exposure = RegisterShaders.getPbr().getUniform("Exposure");
        if (exposure != null) exposure.set(1.0f);

        Uniform ambientStrength = RegisterShaders.getPbr().getUniform("AmbientStrength");
        if (ambientStrength != null) ambientStrength.set(1.0f);

        RenderSystem.enableDepthTest();
        renderOpaque(new PoseStack());
        renderTransparent(new PoseStack());

        VertexBuffer.unbind();
        opaqueEntries.clear();
        transparentEntries.clear();
        models.clear();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    private void renderOpaque(PoseStack poseStack) {
        for (var matEntry : opaqueEntries.entrySet()) {
            matEntry.getKey().bind();
            GL43.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, 0, boneMatrixSsbo.ssbo);
            for (var bufferEntry : matEntry.getValue().entrySet()) {
                VertexBuffer buffer = bufferEntry.getKey();
                buffer.bind();
                for (RenderEntry entry : bufferEntry.getValue()) {
                    draw(poseStack, entry, buffer);
                }
            }
        }
    }

    private void renderTransparent(PoseStack poseStack) {
        transparentEntries.sort((a, b) -> Float.compare(b.distanceSq(), a.distanceSq()));

        for (RenderEntry entry : transparentEntries) {
            entry.model().getMaterial().bind();
            GL43.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, 0, boneMatrixSsbo.ssbo);
            VertexBuffer buffer = entry.model().getVertexBuffer();
            buffer.bind();
            draw(poseStack, entry, buffer);
        }
    }

    public void renderDirect(RenderableModel model, PoseStack poseStack, boolean gui) {
        Minecraft mc = Minecraft.getInstance();

        List<Matrix4f> allBones = new ArrayList<>();
        modelOffsets.clear();

        int cursor = 0;

        if (model.animator != null) {
            float renderTime = mc.level.getGameTime() / 20.0f;
            model.animator.update(renderTime);

            Matrix4f[] bones = model.animator.getFinalMatrices();
            modelOffsets.put(model, cursor);

            for (Matrix4f bone : bones) {
                allBones.add(new Matrix4f(bone));
            }

            cursor += bones.length;
        } else {
            modelOffsets.put(model, -1);
        }

        boneMatrixSsbo.upload(allBones);
        GL43.glMemoryBarrier(GL43.GL_SHADER_STORAGE_BARRIER_BIT);

        RenderSystem.setShader(RegisterShaders::getPbr);

        Uniform viewMat = RegisterShaders.getPbr().getUniform("ViewMat");
        if (viewMat != null) {
            if (gui) {
                viewMat.set(new Matrix4f().identity());
            } else {
                PoseStack camStack = new PoseStack();
                var camera = mc.gameRenderer.getMainCamera();
                camStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
                camStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
                viewMat.set(camStack.last().pose());
            }
        }

        if (gui) {
            RenderSystem.disableDepthTest();
        } else {
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
        }

        Matrix4f baseMat = model.modelMatrix != null
                ? new Matrix4f(model.modelMatrix)
                : (poseStack != null ? new Matrix4f(poseStack.last().pose()) : new Matrix4f().identity());

        Matrix4f projection = RenderSystem.getProjectionMatrix();

        for (AssimpModel part : model.getModel()) {
            part.getMaterial().bind();
            GL43.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, 0, boneMatrixSsbo.ssbo);

            VertexBuffer buffer = part.getVertexBuffer();
            buffer.bind();

            for (AssimpModel.MeshInstanceTransform t : part.instances) {
                PoseStack modelStack = new PoseStack();

                modelStack.mulPose(baseMat);

                if (gui) {
                    modelStack.translate(0.0f, 0.0f, -0.01f);
                }

                modelStack.mulPose(t.global());

                Matrix4f modelMatVal = modelStack.last().pose();

                Uniform modelMat = RegisterShaders.getPbr().getUniform("ModelMat");
                if (modelMat != null) {
                    modelMat.set(modelMatVal);
                }

                Uniform off = RegisterShaders.getPbr().getUniform("Offset");
                if (off != null) {
                    off.set(modelOffsets.getOrDefault(model, -1));
                }

                buffer.drawWithShader(
                        modelMatVal,
                        projection,
                        RenderSystem.getShader()
                );
            }
        }

        VertexBuffer.unbind();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    private void draw(PoseStack poseStack, RenderEntry entry, VertexBuffer buffer) {
        for (AssimpModel.MeshInstanceTransform t : entry.model().instances) {
            poseStack.pushPose();

            poseStack.mulPose(entry.modelWrapper().modelMatrix);
            poseStack.mulPose(t.global());

            Uniform modelMat = RegisterShaders.getPbr().getUniform("ModelMat");
            if (modelMat != null) {
                modelMat.set(poseStack.last().pose());
            }

            Integer offset = modelOffsets.get(entry.modelWrapper());
            Uniform off = RegisterShaders.getPbr().getUniform("Offset");
            if (off != null) {
                off.set(offset == null ? -1 : offset);
            }

            buffer.drawWithShader(
                    poseStack.last().pose(),
                    RenderSystem.getProjectionMatrix(),
                    RenderSystem.getShader()
            );

            poseStack.popPose();
        }
    }

    public record RenderEntry(
            AssimpModel model,
            RenderableModel modelWrapper,
            float distanceSq
    ) {}
}