package com.noodlegamer76.shadered.mixin;

import com.noodlegamer76.shadered.event.RenderEventsForRenderTargets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.irisshaders.iris.shaderpack.IdMap;
import net.irisshaders.iris.shaderpack.materialmap.BlockEntry;
import net.irisshaders.iris.shaderpack.option.ShaderPackOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

@Mixin(value = IdMap.class, remap = false)
public class IrisIdMapMixin {

    @Shadow private Int2ObjectMap<List<BlockEntry>> blockPropertiesMap;

    /**
     * Inject into parseBlockMap. We always create a mutable copy, add our custom block, then wrap the map.
     */
    @Inject(method = "parseBlockMap", at = @At("RETURN"), cancellable = true, remap = false)
    private static void onParseBlockMap(Properties properties, String keyPrefix, String fileName,
                                        CallbackInfoReturnable<Int2ObjectMap<List<BlockEntry>>> cir) {
        // Always create a mutable copy even if the original isn't null.
        Int2ObjectMap<List<BlockEntry>> original = cir.getReturnValue();
        Int2ObjectMap<List<BlockEntry>> mutableMap = new Int2ObjectOpenHashMap<>();
        if (original != null) {
            mutableMap.putAll(original);
        }
        addCustomBlockEntry(mutableMap);
        // Replace the return value with an unmodifiable version of the updated map.
        cir.setReturnValue(Int2ObjectMaps.unmodifiable(mutableMap));
    }

    /**
     * Inject into the IdMap constructor to update the shadow field.
     */
    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void onIdMapInit(Path shaderPath, ShaderPackOptions shaderPackOptions, Iterable environmentDefines,
                             CallbackInfo ci) {
        // Create a mutable copy of the current blockPropertiesMap.
        Int2ObjectMap<List<BlockEntry>> mutableMap = new Int2ObjectOpenHashMap<>(blockPropertiesMap);
        addCustomBlockEntry(mutableMap);
        // Update the shadow field with the unmodifiable version.
        blockPropertiesMap = Int2ObjectMaps.unmodifiable(mutableMap);
    }

    /**
     * Finds an available ID, assigns it to skyblockId, and adds a custom block entry.
     */
    private static void addCustomBlockEntry(Int2ObjectMap<List<BlockEntry>> map) {
        int id = 1;
        while (id < 32767 && map.containsKey(id)) {
            id++;
        }
        if (id >= 32767) {
            RenderEventsForRenderTargets.skyblockId = Integer.MIN_VALUE;
            System.out.println("No free block ID found, using fallback.");
            return;
        }
        RenderEventsForRenderTargets.skyblockId = id;
        map.put(id, List.of(BlockEntry.parse("shadered:space_block")));
        System.out.println("Custom block added WITH ID: " + id);
    }
}
