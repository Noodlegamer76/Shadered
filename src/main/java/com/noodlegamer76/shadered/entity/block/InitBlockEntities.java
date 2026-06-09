package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.*;
import com.noodlegamer76.shadered.entity.block.old.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ShaderedMod.MODID);

        public static final RegistryObject<BlockEntityType<RenderTester>> RENDER_TESTER = BLOCK_ENTITIES.register("render_tester",
            () -> BlockEntityType.Builder.of(RenderTester::new, InitBlocks.RENDER_TESTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyblockEntity>> SKYBLOCK = BLOCK_ENTITIES.register("skyblock",
            () -> BlockEntityType.Builder.of(SkyblockEntity::new, InitBlocks.SKYBLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyEmitterEntity>> SKY_EMITTER = BLOCK_ENTITIES.register("sky_emitter",
            () -> BlockEntityType.Builder.of(SkyEmitterEntity::new, InitBlocks.SKY_EMITTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<LightBulbEntity>> LIGHT_BULB = BLOCK_ENTITIES.register("light_bulb",
            () -> BlockEntityType.Builder.of(LightBulbEntity::new, InitBlocks.LIGHT_BULB.get()).build(null));

    public static final RegistryObject<BlockEntityType<MaxwellEntity>> MAXWELL = BLOCK_ENTITIES.register("maxwell",
            () -> BlockEntityType.Builder.of(MaxwellEntity::new, InitBlocks.MAXWELL.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpaceCompressorBlockEntity>> SPACE_COMPRESSOR = BLOCK_ENTITIES.register("space_compressor",
            () -> BlockEntityType.Builder.of(SpaceCompressorBlockEntity::new, InitBlocks.SPACE_COMPRESSOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<FilterBlockEntity>> FILTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("filter_block_entity",
            () -> BlockEntityType.Builder.of(FilterBlockEntity::new, InitBlocks.FILTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<IllusoriteOreBlockEntity>> ILLUSORITE_BLOCK_ENTITY = BLOCK_ENTITIES.register("illusorite_block_entity",
            () -> BlockEntityType.Builder.of(IllusoriteOreBlockEntity::new, InitBlocks.ILLUSORITE_ORE.get(), InitBlocks.DEEPSLATE_ILLUSORITE_ORE.get()).build(null));

    //DEPRECATED BLOCK ENTITIES

    public static final RegistryObject<BlockEntityType<SpaceBlockEntity>> SPACE_BLOCK = BLOCK_ENTITIES.register("space_block",
            () -> BlockEntityType.Builder.of(SpaceBlockEntity::new, InitBlocks.SPACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<StormyBlockEntity>> STORMY_BLOCK = BLOCK_ENTITIES.register("stormy_block",
            () -> BlockEntityType.Builder.of(StormyBlockEntity::new, InitBlocks.STORMY_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<OceanBlockEntity>> OCEAN_BLOCK = BLOCK_ENTITIES.register("ocean_block",
            () -> BlockEntityType.Builder.of(OceanBlockEntity::new, InitBlocks.OCEAN_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EndBlockEntity>> END_BLOCK = BLOCK_ENTITIES.register("end_block",
            () -> BlockEntityType.Builder.of(EndBlockEntity::new, InitBlocks.END_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<IridiaBlockEntity>> IRIDIA_BLOCK = BLOCK_ENTITIES.register("iridia_block",
            () -> BlockEntityType.Builder.of(IridiaBlockEntity::new, InitBlocks.IRIDIA_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EndSkyBlockEntity>> END_SKY_BLOCK = BLOCK_ENTITIES.register("end_sky_block",
            () -> BlockEntityType.Builder.of(EndSkyBlockEntity::new, InitBlocks.END_SKY_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EclipseBlockEntity>> ECLIPSE_BLOCK = BLOCK_ENTITIES.register("eclipse_block",
            () -> BlockEntityType.Builder.of(EclipseBlockEntity::new, InitBlocks.ECLIPSE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ForestBlockEntity>> FOREST_BLOCK = BLOCK_ENTITIES.register("forest_block",
            () -> BlockEntityType.Builder.of(ForestBlockEntity::new, InitBlocks.FOREST_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MimicBlockEntity>> MIMIC_BLOCK = BLOCK_ENTITIES.register("mimic_block",
            () -> BlockEntityType.Builder.of(MimicBlockEntity::new, InitBlocks.MIMIC_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<LightBlockEntity>> LIGHT_BLOCK = BLOCK_ENTITIES.register("light_block",
            () -> BlockEntityType.Builder.of(LightBlockEntity::new, InitBlocks.LIGHT_BLOCK.get()).build(null));

}