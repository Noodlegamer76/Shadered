package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.Shadered;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Shadered.MODID);

    //public static final DeferredBlock<NihilPortal> NIHIL_PORTAL = BLOCKS.register("nihil_portal",
    //        () -> new NihilPortal(BlockBehaviour.Properties.of().noOcclusion()));
}
