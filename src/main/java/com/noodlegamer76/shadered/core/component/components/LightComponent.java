package com.noodlegamer76.shadered.core.component.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.util.shader.lights.Light;
import com.noodlegamer76.shadered.client.util.shader.lights.LightUploader;
import com.noodlegamer76.shadered.core.component.Component;
import com.noodlegamer76.shadered.core.component.ComponentType;
import com.noodlegamer76.shadered.core.component.InitComponents;
import com.noodlegamer76.shadered.core.network.GameObjectSerializers;
import com.noodlegamer76.shadered.core.network.SyncedVar;
import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class LightComponent extends Component implements RenderableComponent {
    private final SyncedVar<Float> red = new SyncedVar<>(this, 1.0f, GameObjectSerializers.FLOAT);
    private final SyncedVar<Float> green = new SyncedVar<>(this, 1.0f, GameObjectSerializers.FLOAT);
    private final SyncedVar<Float> blue = new SyncedVar<>(this, 1.0f, GameObjectSerializers.FLOAT);
    private final SyncedVar<Float> radius = new SyncedVar<>(this, 10.0f, GameObjectSerializers.FLOAT);

    public LightComponent(GameObject gameObject) {
        super(InitComponents.LIGHT, gameObject);
    }

    public float getRed() {
        return red.getValue();
    }

    public float getGreen() {
        return green.getValue();
    }

    public float getBlue() {
        return blue.getValue();
    }

    public float getRadius() {
        return radius.getValue();
    }

    public void setColor(float red, float green, float blue) {
        this.red.setValue(red, true);
        this.green.setValue(green, true);
        this.blue.setValue(blue, true);
    }

    public void setRadius(float radius) {
        this.radius.setValue(radius, true);
    }

    @Override
    public void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        red.setValue(tag.getFloat("red"), true);
        green.setValue(tag.getFloat("green"), true);
        blue.setValue(tag.getFloat("blue"), true);
        radius.setValue(tag.getFloat("radius"), true);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("red", red.getValue());
        tag.putFloat("green", green.getValue());
        tag.putFloat("blue", blue.getValue());
        tag.putFloat("radius", radius.getValue());
    }

    @Override
    public List<SyncedVar<?>> getSyncedData() {
        return super.getSyncedData();
    }

    @Override
    public void render(GameObject entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Light light = new Light(
                (float) entity.getX(),
                (float) entity.getY(),
                (float) entity.getZ(),
                red.getValue(),
                green.getValue(),
                blue.getValue(),
                radius.getValue()
        );
        LightUploader.addLight(light);
    }
}
