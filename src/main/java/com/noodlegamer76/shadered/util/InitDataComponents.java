package com.noodlegamer76.shadered.core.component;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.item.SkyblockData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ShaderedMod.MODID);

    public static final Supplier<DataComponentType<SkyblockData>> SKYBLOCK_DATA = COMPONENT_TYPES.register(
            "skyblock_data",
            () -> DataComponentType.<SkyblockData>builder()
                    .persistent(SkyblockData.CODEC)
                    .networkSynchronized(ByteBufCodecs.fromCodecWithRegistries(SkyblockData.CODEC))
                    .build()
    );
}