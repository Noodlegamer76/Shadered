package com.noodlegamer76.shadered.tile;

import com.noodlegamer76.shadered.Shadered;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Shadered.MODID);

    //public static final Supplier<BlockEntityType<NihilPortalTile>> NIHIL_PORTAL = BLOCK_ENTITIES.register("nihil_portal",
    //        () -> BlockEntityType.Builder.of(NihilPortalTile::new, InitBlocks.NIHIL_PORTAL.get()).build(null));
}
