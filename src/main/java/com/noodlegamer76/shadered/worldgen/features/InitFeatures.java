package com.noodlegamer76.shadered.worldgen.features;

import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ShaderedMod.MODID);

    public static final DeferredHolder<Feature<?>, IllusoriteOreFeature> ILLUSORITE_ORE = FEATURES.register("illusorite_ore",
            () -> new IllusoriteOreFeature(OreConfiguration.CODEC));
}
