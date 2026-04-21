package com.noodlegamer76.shadered.entity;

import com.noodlegamer76.shadered.core.ComponentManager;
import com.noodlegamer76.shadered.core.component.Component;
import com.noodlegamer76.shadered.core.component.InitComponents;
import com.noodlegamer76.shadered.network.ComponentPacket;
import com.noodlegamer76.shadered.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

public class GameObject extends Entity {
    private static final EntityDataAccessor<Vector3f> SCALE = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> PHYS_POS = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> LINEAR_VELOCITY = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> ANGULAR_VELOCITY = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Quaternionf> ROTATION = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.QUATERNION);
    private static final EntityDataAccessor<CompoundTag> COMPONENT_UPDATE_DATA = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<CompoundTag> COMPONENT_DELETE_DATA = SynchedEntityData.defineId(GameObject.class, EntityDataSerializers.COMPOUND_TAG);
    public Quaternionf prevRotation = new Quaternionf();
    private final ComponentManager componentManager = new ComponentManager();
    private int nextId = 0;

    public GameObject(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(SCALE, new Vector3f(1, 1, 1));
        entityData.define(PHYS_POS, new Vector3f());
        entityData.define(ROTATION, new Quaternionf());
        entityData.define(LINEAR_VELOCITY, new Vector3f());
        entityData.define(ANGULAR_VELOCITY, new Vector3f());
        entityData.define(COMPONENT_UPDATE_DATA, new CompoundTag());
        entityData.define(COMPONENT_DELETE_DATA, new CompoundTag());
    }

    public void addComponent(Component component) {
        componentManager.addComponent(component);
    }

    public void removeComponent(Component component) {
        componentManager.removeComponent(component);
    }

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();

        for (Component component: new ArrayList<>(componentManager.getComponents().values())) {
            removeComponent(component);
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        byte[] state = componentManager.getLastFullState();
        if (state == null) return;
        if (state.length > 0) {
            ComponentPacket payload = new ComponentPacket(getId(), state);
            PacketHandler.sendToPlayer(serverPlayer, payload);
        }
    }

    @Override
    public void tick() {
        super.tick();
        prevRotation.set(entityData.get(ROTATION));
        componentManager.tick(this);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        CompoundTag noodleEngineTag = tag.getCompound("noodleengine");
        nextId = noodleEngineTag.getInt("nextId");
        Vector3f scale = new Vector3f(noodleEngineTag.getFloat("scaleX"), noodleEngineTag.getFloat("scaleY"), noodleEngineTag.getFloat("scaleZ"));
        entityData.set(SCALE, scale);
        Quaternionf rotation = new Quaternionf(noodleEngineTag.getFloat("rotation_x"), noodleEngineTag.getFloat("rotation_y"), noodleEngineTag.getFloat("rotation_z"), noodleEngineTag.getFloat("rotation_w"));
        entityData.set(ROTATION, rotation);


        CompoundTag componentTag = noodleEngineTag.getCompound("components");
        componentTag.getAllKeys().forEach(key -> {
            int id = Integer.parseInt(key);
            CompoundTag componentTagData = componentTag.getCompound(key);
            Component component;
            if (!componentManager.getComponents().containsKey(id)) {
                ResourceLocation type = ResourceLocation.tryParse(componentTagData.getString("type"));
                component = InitComponents.getComponentType(type).create(this);
                component.setId(id);
                addComponent(component);
            }
            else {
                component = componentManager.getComponents().get(id);
            }
            component.load(componentTagData);
            if (componentManager.getComponents().containsKey(component.getId())) {
                componentManager.getComponents().replace(component.getId(), component);
            }
            component.onAdded(level());
        });
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        CompoundTag noodleEngineTag = new CompoundTag();
        noodleEngineTag.putInt("nextId", nextId);
        Vector3f scale = entityData.get(SCALE);
        noodleEngineTag.putFloat("scaleX", scale.x);
        noodleEngineTag.putFloat("scaleY", scale.y);
        noodleEngineTag.putFloat("scaleZ", scale.z);
        Quaternionf rotation = entityData.get(ROTATION);
        noodleEngineTag.putFloat("rotation_x", rotation.x);
        noodleEngineTag.putFloat("rotation_y", rotation.y);
        noodleEngineTag.putFloat("rotation_z", rotation.z);
        noodleEngineTag.putFloat("rotation_w", rotation.w);

        CompoundTag componentTag = new CompoundTag();
        for (Component component : componentManager.getComponents().values()) {
            component.save(componentTag);
        }
        noodleEngineTag.put("components", componentTag);
        tag.put("noodleengine", noodleEngineTag);
    }

    public Vector3f getScale() {
        return entityData.get(SCALE);
    }

    public void setScale(Vector3f scale) {
        entityData.set(SCALE, scale);
    }

    public Quaternionf getRotation() {
        return entityData.get(ROTATION);
    }

    public void setRotation(Quaternionf rotation) {
        entityData.set(ROTATION, rotation);
    }

    public Vector3f getLinearVelocity() {
        return entityData.get(LINEAR_VELOCITY);
    }

    public void setLinearVelocity(Vector3f linearVelocity) {
        entityData.set(LINEAR_VELOCITY, linearVelocity);
    }

    public Vector3f getAngularVelocity() {
        return entityData.get(ANGULAR_VELOCITY);
    }

    public void setAngularVelocity(Vector3f angularVelocity) {
        entityData.set(ANGULAR_VELOCITY, angularVelocity);
    }

    public int nextId() {
        return nextId++;
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public static EntityDataAccessor<CompoundTag> getComponentDeleteData() {
        return COMPONENT_DELETE_DATA;
    }

    public static EntityDataAccessor<CompoundTag> getComponentUpdateData() {
        return COMPONENT_UPDATE_DATA;
    }

    public void setPhysicsPosition(Vector3f position) {
        entityData.set(PHYS_POS, position);
    }

    public Vector3f getPhysicsPosition() {
        return entityData.get(PHYS_POS);
    }
}
