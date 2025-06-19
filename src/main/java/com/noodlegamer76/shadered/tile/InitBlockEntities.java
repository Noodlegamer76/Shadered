package com.noodlegamer76.shadered.tile;

import com.noodlegamer76.shadered.Shadered;
import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Shadered.MODID);

    public static final Supplier<BlockEntityType<SpaceBlockEntity>> SPACE_BLOCK = BLOCK_ENTITIES.register("space_block",
            () -> BlockEntityType.Builder.of(SpaceBlockEntity::new, InitBlocks.SPACE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<OceanBlockEntity>> OCEAN_BLOCK = BLOCK_ENTITIES.register("ocean_block",
            () -> BlockEntityType.Builder.of(OceanBlockEntity::new, InitBlocks.OCEAN_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<StormyBlockEntity>> STORMY_BLOCK = BLOCK_ENTITIES.register("stormy_block",
            () -> BlockEntityType.Builder.of(StormyBlockEntity::new, InitBlocks.STORMY_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<EndBlockEntity>> END_BLOCK = BLOCK_ENTITIES.register("end_block",
            () -> BlockEntityType.Builder.of(EndBlockEntity::new, InitBlocks.END_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<EndSkyBlockEntity>> END_SKY_BLOCK = BLOCK_ENTITIES.register("end_sky_block",
            () -> BlockEntityType.Builder.of(EndSkyBlockEntity::new, InitBlocks.END_SKY_BLOCK.get()).build(null));
}
