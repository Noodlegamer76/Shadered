package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ShaderedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockTagGenerator modBlockTagGenerator = new ModBlockTagGenerator(packOutput, lookupProvider, ShaderedMod.MODID, existingFileHelper);


        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        // generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(true, modBlockTagGenerator);
        generator.addProvider(true, new ModItemTagGenerator(packOutput, lookupProvider, modBlockTagGenerator.contentsGetter() , existingFileHelper));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
    }
}
