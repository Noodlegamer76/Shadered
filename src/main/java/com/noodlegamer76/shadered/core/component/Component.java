package com.noodlegamer76.shadered.core.component;

import com.noodlegamer76.shadered.core.network.SyncedVar;
import com.noodlegamer76.shadered.core.network.SyncedVarOwner;
import com.noodlegamer76.shadered.entity.GameObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public abstract class Component implements SyncedVarOwner {
    protected final GameObject gameObject;
    protected final DeferredHolder<ComponentType<?>, ComponentType<?>> type;
    public int id;

    protected Component(DeferredHolder<ComponentType<?>, ComponentType<?>> type, GameObject gameObject) {
        this.gameObject = gameObject;
        this.type = type;
        this.id = gameObject.nextId();
    }

    public void onAdded(Level level) {

    }

    public void onUpdated(Level level) {

    }

    public void onRemoved(Level level) {

    }

    public void tick() {

    }

    public DeferredHolder<ComponentType<?>, ComponentType<?>> getType() {
        return type;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void save(CompoundTag tag) {
        CompoundTag componentTag = new CompoundTag();
        componentTag.putString("type", type.getId().toString());
        saveAdditional(componentTag);
        tag.put(String.valueOf(id), componentTag);
    }

    public void saveAdditional(CompoundTag tag) {}

    public void load(CompoundTag tag) {
        loadAdditional(tag);
    }

    public void loadAdditional(CompoundTag tag) {}

    @Override
    public List<SyncedVar<?>> getSyncedData() {
        return List.of();
    }

    @Override
    public void markDirty(SyncedVar<?> var) {
        gameObject.getComponentManager().markDirty(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
