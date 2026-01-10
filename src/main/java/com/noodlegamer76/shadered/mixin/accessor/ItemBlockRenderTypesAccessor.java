package com.noodlegamer76.shadered.mixin.accessor;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemBlockRenderTypes.class)
public interface ItemBlockRenderTypesAccessor {

    @Accessor(
            value = "TYPE_BY_BLOCK"
    )
    static Map<Block, RenderType> shaderedGetTypeByBlock() {
        throw new AssertionError();
    }
}
