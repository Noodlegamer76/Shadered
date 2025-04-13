package com.noodlegamer76.shadered.tile;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.InitBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ShaderedMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<SkyBlock>> SKY_BLOCK = BLOCK_ENTITIES.register("sky_block",
            () -> BlockEntityType.Builder.of(
                    SkyBlock::new,
                    InitBlocks.SPACE_BLOCK.get(),
                    InitBlocks.OCEAN_BLOCK.get()
            ).build(null));
}
