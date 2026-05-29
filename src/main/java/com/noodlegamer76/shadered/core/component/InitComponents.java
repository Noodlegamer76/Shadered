package com.noodlegamer76.shadered.core.component;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.core.component.components.MeshComponent;
import com.noodlegamer76.shadered.entity.GameObject;
import com.noodlegamer76.shadered.event.ShaderedRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitComponents {
    public static final DeferredRegister<ComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(ShaderedRegistries.COMPONENT_TYPE, ShaderedMod.MODID);

    public static final DeferredHolder<ComponentType<?>, ?> MESH = COMPONENT_TYPES.register("mesh",
            () -> new ComponentType<>(MeshComponent::new));

    @FunctionalInterface
    public interface ComponentSupplier<T extends Component> {
        T create(GameObject var1);
    }

    public static ComponentType<?> getComponentType(ResourceLocation id) {
        return InitComponents.COMPONENT_TYPES.getEntries().stream()
                .filter(holder -> holder.getId().equals(id))
                .map(DeferredHolder::get)
                .findFirst()
                .orElse(null);
    }
}
