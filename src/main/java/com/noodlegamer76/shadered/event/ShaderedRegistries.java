package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.core.component.ComponentType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = ShaderedMod.MODID)
public class ShaderedRegistries {
    public static final ResourceKey<Registry<ComponentType<?>>> COMPONENT_TYPE = createRegistryKey("component_type");
    public static Registry<ComponentType<?>> COMPONENT_TYPES;

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, name));
    }

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        COMPONENT_TYPES = event.create(new RegistryBuilder<>(COMPONENT_TYPE));
    }
}
