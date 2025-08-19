package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.EclipseBlock;
import com.noodlegamer76.shadered.block.InitBlocks;
import com.noodlegamer76.shadered.block.StormyBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ShaderedMod.MODID);

        public static final RegistryObject<BlockEntityType<RenderTester>> RENDER_TESTER = BLOCK_ENTITIES.register("render_tester",
            () -> BlockEntityType.Builder.of(RenderTester::new, InitBlocks.RENDER_TESTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpaceBlockEntity>> SPACE_BLOCK = BLOCK_ENTITIES.register("space_block",
            () -> BlockEntityType.Builder.of(SpaceBlockEntity::new, InitBlocks.SPACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<StormyBlockEntity>> STORMY_BLOCK = BLOCK_ENTITIES.register("stormy_block",
            () -> BlockEntityType.Builder.of(StormyBlockEntity::new, InitBlocks.STORMY_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<OceanBlockEntity>> OCEAN_BLOCK = BLOCK_ENTITIES.register("ocean_block",
            () -> BlockEntityType.Builder.of(OceanBlockEntity::new, InitBlocks.OCEAN_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EndBlockEntity>> END_BLOCK = BLOCK_ENTITIES.register("end_block",
            () -> BlockEntityType.Builder.of(EndBlockEntity::new, InitBlocks.END_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EndSkyBlockEntity>> END_SKY_BLOCK = BLOCK_ENTITIES.register("end_sky_block",
            () -> BlockEntityType.Builder.of(EndSkyBlockEntity::new, InitBlocks.END_SKY_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EclipseBlockEntity>> ECLIPSE_BLOCK = BLOCK_ENTITIES.register("eclipse_block",
            () -> BlockEntityType.Builder.of(EclipseBlockEntity::new, InitBlocks.ECLIPSE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpaceCompressorBlockEntity>> SPACE_COMPRESSOR = BLOCK_ENTITIES.register("space_compressor",
            () -> BlockEntityType.Builder.of(SpaceCompressorBlockEntity::new, InitBlocks.SPACE_COMPRESSOR.get()).build(null));
}