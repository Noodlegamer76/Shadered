package com.noodlegamer76.shadered.worldgen.features;

import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ShaderedMod.MODID);

    public static final RegistryObject<IllusoriteOreFeature> ILLUSORITE_ORE = FEATURES.register("illusorite_ore",
            () -> new IllusoriteOreFeature(OreConfiguration.CODEC));
}
