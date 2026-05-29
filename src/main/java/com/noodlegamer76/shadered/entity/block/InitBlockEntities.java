package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ShaderedMod.MODID);

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RenderTester>> RENDER_TESTER = BLOCK_ENTITIES.register("render_tester",
            () -> BlockEntityType.Builder.of(RenderTester::new, InitBlocks.RENDER_TESTER_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpaceBlockEntity>> SPACE_BLOCK = BLOCK_ENTITIES.register("space_block",
            () -> BlockEntityType.Builder.of(SpaceBlockEntity::new, InitBlocks.SPACE_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StormyBlockEntity>> STORMY_BLOCK = BLOCK_ENTITIES.register("stormy_block",
            () -> BlockEntityType.Builder.of(StormyBlockEntity::new, InitBlocks.STORMY_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OceanBlockEntity>> OCEAN_BLOCK = BLOCK_ENTITIES.register("ocean_block",
            () -> BlockEntityType.Builder.of(OceanBlockEntity::new, InitBlocks.OCEAN_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EndBlockEntity>> END_BLOCK = BLOCK_ENTITIES.register("end_block",
            () -> BlockEntityType.Builder.of(EndBlockEntity::new, InitBlocks.END_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IridiaBlockEntity>> IRIDIA_BLOCK = BLOCK_ENTITIES.register("iridia_block",
            () -> BlockEntityType.Builder.of(IridiaBlockEntity::new, InitBlocks.IRIDIA_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EndSkyBlockEntity>> END_SKY_BLOCK = BLOCK_ENTITIES.register("end_sky_block",
            () -> BlockEntityType.Builder.of(EndSkyBlockEntity::new, InitBlocks.END_SKY_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EclipseBlockEntity>> ECLIPSE_BLOCK = BLOCK_ENTITIES.register("eclipse_block",
            () -> BlockEntityType.Builder.of(EclipseBlockEntity::new, InitBlocks.ECLIPSE_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForestBlockEntity>> FOREST_BLOCK = BLOCK_ENTITIES.register("forest_block",
            () -> BlockEntityType.Builder.of(ForestBlockEntity::new, InitBlocks.FOREST_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MimicBlockEntity>> MIMIC_BLOCK = BLOCK_ENTITIES.register("mimic_block",
            () -> BlockEntityType.Builder.of(MimicBlockEntity::new, InitBlocks.MIMIC_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LightBlockEntity>> LIGHT_BLOCK = BLOCK_ENTITIES.register("light_block",
            () -> BlockEntityType.Builder.of(LightBlockEntity::new, InitBlocks.LIGHT_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyEmitterEntity>> SKY_EMITTER = BLOCK_ENTITIES.register("sky_emitter",
            () -> BlockEntityType.Builder.of(SkyEmitterEntity::new, InitBlocks.SKY_EMITTER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MaxwellEntity>> MAXWELL = BLOCK_ENTITIES.register("maxwell",
            () -> BlockEntityType.Builder.of(MaxwellEntity::new, InitBlocks.MAXWELL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WindowEntity>> WINDOW = BLOCK_ENTITIES.register("window",
            () -> BlockEntityType.Builder.of(WindowEntity::new, InitBlocks.WINDOW.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpaceCompressorBlockEntity>> SPACE_COMPRESSOR = BLOCK_ENTITIES.register("space_compressor",
            () -> BlockEntityType.Builder.of(SpaceCompressorBlockEntity::new, InitBlocks.SPACE_COMPRESSOR.get()).build(null));
}