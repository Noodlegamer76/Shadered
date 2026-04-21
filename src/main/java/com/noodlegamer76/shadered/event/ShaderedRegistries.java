package com.noodlegamer76.shadered.event;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.core.component.ComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderedRegistries {
    public static final ResourceLocation COMPONENT_TYPE = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "component_type");

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.create(new RegistryBuilder<ComponentType<?>>()
                .setName(COMPONENT_TYPE));
    }
}
