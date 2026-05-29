package com.noodlegamer76.shadered.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = 
            DeferredRegister.createDataComponents("shadered");

    public static final Supplier<DataComponentType<BlockPos>> COMPRESSOR_POS = 
            DATA_COMPONENT_TYPES.register("compressor_pos", () -> DataComponentType.<BlockPos>builder()
                    .persistent(BlockPos.CODEC)
                    .build());
}