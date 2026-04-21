package com.noodlegamer76.shadered.core;

import com.noodlegamer76.shadered.core.component.Component;
import com.noodlegamer76.shadered.core.component.InitComponents;
import com.noodlegamer76.shadered.core.network.SyncedVar;
import com.noodlegamer76.shadered.entity.GameObject;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentManager {
    private final Map<Integer, Component> components = new HashMap<>();
    private final List<Component> dirtyUpdate = new ArrayList<>();
    private final List<Component> dirtyRemove = new ArrayList<>();
    private byte[] lastFullState;
    private int lastRemoveId = 0;
    private int lastUpdateId = 0;

    public int nextRemoveId() {
        return ++lastRemoveId;
    }

    public int nextUpdateId() {
        return ++lastUpdateId;
    }

    public void addComponent(Component component) {
        if (component.getId() == 0)
            component.setId(component.getGameObject().nextId());
        components.put(component.getId(), component);
        dirtyUpdate.add(component);
        markDirty(component);
        component.onAdded(component.getGameObject().level());
    }

    public void removeComponent(Component component) {
        if (!components.containsKey(component.getId())) return;
        components.remove(component.getId());
        dirtyRemove.add(component);
        component.onRemoved(component.getGameObject().level());
        updateFullState();
    }

    public void markDirty(Component component) {
        if (!dirtyUpdate.contains(component))
            dirtyUpdate.add(component);
        updateFullState();
    }

    private void updateFullState() {
        lastFullState = saveComponents(new ArrayList<>(components.values()));
    }

    public void tick(GameObject gameObject) {
        if (!gameObject.level().isClientSide) {
            boolean sendRemoval = !dirtyRemove.isEmpty();
            boolean sendUpdate = !dirtyUpdate.isEmpty();

            if (sendRemoval) {
                CompoundTag tag = new CompoundTag();
                CompoundTag removed = new CompoundTag();
                for (Component c : dirtyRemove)
                    removed.putInt(String.valueOf(c.getId()), 1);
                tag.put("components", removed);
                tag.putInt("id", nextRemoveId());
                gameObject.getEntityData().set(GameObject.getComponentDeleteData(), tag);
                dirtyRemove.clear();
            }

            if (sendUpdate) {
                byte[] arr = saveComponents(dirtyUpdate);
                CompoundTag tag = new CompoundTag();
                tag.putInt("id", nextUpdateId());
                tag.putByteArray("components", arr);
                gameObject.getEntityData().set(GameObject.getComponentUpdateData(), tag);
                dirtyUpdate.clear();
            }
        } else {
            CompoundTag update = gameObject.getEntityData().get(GameObject.getComponentUpdateData());
            CompoundTag remove = gameObject.getEntityData().get(GameObject.getComponentDeleteData());

            if (update.getInt("id") != lastUpdateId) {
                loadComponents(gameObject, update.getByteArray("components"));
                lastUpdateId = update.getInt("id");
            }
            if (remove.getInt("id") != lastRemoveId) {
                for (String key : remove.getCompound("components").getAllKeys()) {
                    int id = Integer.parseInt(key);
                    Component c = components.get(id);
                    if (c != null) removeComponent(c);
                }
                lastRemoveId = remove.getInt("id");
            }
        }

        for (Component component: components.values()) {
            component.tick();
        }
    }

    @SuppressWarnings("unchecked")
    public byte[] saveComponents(List<Component> comps) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(comps.size());
        for (Component c : comps) {
            buf.writeInt(c.getId());
            buf.writeResourceLocation(c.getType().getId());
            List<SyncedVar<?>> vars = c.getSyncedData();
            buf.writeVarInt(vars.size());
            for (SyncedVar<?> v : vars)
                ((SyncedVar<Object>) v).getSerializer().serialize(buf, v.getValue());
        }
        byte[] arr = new byte[buf.readableBytes()];
        buf.getBytes(0, arr);
        buf.release();
        return arr;
    }

    @SuppressWarnings("unchecked")
    public void loadComponents(GameObject gameObject, byte[] data) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
        int count = buf.readInt();
        for (int i = 0; i < count; i++) {
            int id = buf.readInt();
            ResourceLocation type = buf.readResourceLocation();
            Component c = components.get(id);
            if (c == null) {
                c = InitComponents.getComponentType(type).create(gameObject);
                c.setId(id);
                components.put(id, c);
                c.onAdded(gameObject.level());
            }
            int varCount = buf.readVarInt();
            List<SyncedVar<?>> vars = c.getSyncedData();
            for (int j = 0; j < varCount && j < vars.size(); j++) {
                SyncedVar<Object> v = (SyncedVar<Object>) vars.get(j);
                v.setValue(v.getSerializer().deserialize(buf), false);
            }
            c.onUpdated(gameObject.level());
        }
        buf.release();
    }

    public List<Component> getDirtyRemove() {
        return dirtyRemove;
    }

    public Map<Integer, Component> getComponents() {
        return components;
    }

    public List<Component> getDirtyUpdate() {
        return dirtyUpdate;
    }

    public byte[] getLastFullState() {
        return lastFullState;
    }
}
