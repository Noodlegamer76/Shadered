package com.noodlegamer76.shadered.core.component;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.core.component.components.LightComponent;
import com.noodlegamer76.shadered.entity.GameObject;
import com.noodlegamer76.shadered.event.ShaderedRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitComponents {
    public static final DeferredRegister<ComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(ShaderedRegistries.COMPONENT_TYPE, ShaderedMod.MODID);

   public static final RegistryObject<ComponentType<?>> LIGHT = COMPONENT_TYPES.register("light",
           () -> new ComponentType<>(LightComponent::new));

    @FunctionalInterface
    public interface ComponentSupplier<T extends Component> {
        T create(GameObject var1);
    }

    public static ComponentType<?> getComponentType(ResourceLocation id) {
        return InitComponents.COMPONENT_TYPES.getEntries().stream()
                .filter(holder -> holder.getId().equals(id))
                .map(RegistryObject::get)
                .findFirst()
                .orElse(null);
    }
}
