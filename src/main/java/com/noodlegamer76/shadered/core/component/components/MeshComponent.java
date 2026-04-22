package com.noodlegamer76.shadered.core.component.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.assimp.AssimpModel;
import com.noodlegamer76.shadered.client.assimp.McModel;
import com.noodlegamer76.shadered.client.assimp.load.AssimpModels;
import com.noodlegamer76.shadered.client.renderer.assimp.AssimpRenderer;
import com.noodlegamer76.shadered.client.renderer.assimp.RenderableModel;
import com.noodlegamer76.shadered.client.util.shader.lights.Light;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import com.noodlegamer76.shadered.core.component.Component;
import com.noodlegamer76.shadered.core.component.ComponentType;
import com.noodlegamer76.shadered.core.component.InitComponents;
import com.noodlegamer76.shadered.core.network.GameObjectSerializers;
import com.noodlegamer76.shadered.core.network.SyncedVar;
import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class MeshComponent extends Component implements RenderableComponent {
    private final SyncedVar<ResourceLocation> model = new SyncedVar<>(this, ResourceLocation.withDefaultNamespace("error"), GameObjectSerializers.RESOURCE_LOCATION);
    private RenderableModel renderableModel;
    private ResourceLocation clientLastModel;

    public MeshComponent(GameObject gameObject) {
        super(InitComponents.MESH, gameObject);
    }

    public ResourceLocation getModel() {
        return model.getValue();
    }

    public void setModel(ResourceLocation model) {
        this.model.setValue(model, true);
    }

    @Override
    public void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        this.model.setValue(tag.contains("model") ? ResourceLocation.tryParse(tag.getString("model")) : ResourceLocation.withDefaultNamespace("error"), true);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("model", model.getValue().toString());
    }

    @Override
    public List<SyncedVar<?>> getSyncedData() {
        return List.of(
                model
        );
    }

    @Override
    public void onUpdated(Level level) {
        if (level.isClientSide) {
            ResourceLocation modelLocation = model.getValue();
            if (renderableModel == null || !clientLastModel.equals(modelLocation)) {
                clientLastModel = modelLocation;
                McModel model = AssimpModels.getModel(modelLocation);
                if (model == null) {
                    renderableModel = null;
                    return;
                }

                renderableModel = new RenderableModel();

                for (AssimpModel assimpModel : model.getModels()) {
                    renderableModel.getModel().add(assimpModel);
                }
            }

            clientLastModel = modelLocation;
        }
    }

    @Override
    public void render(GameObject entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (renderableModel == null) return;

        poseStack = new PoseStack();
        poseStack.translate(entity.getX(), entity.getY(), entity.getZ());
        poseStack.mulPose(entity.getRotation());
        poseStack.scale(entity.getScale().x, entity.getScale().y, entity.getScale().z);

        renderableModel.modelMatrix = poseStack.last().pose();

        AssimpRenderer renderer = AssimpRenderer.getInstance();
        renderer.addModel(renderableModel);
    }
}
