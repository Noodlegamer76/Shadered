package com.noodlegamer76.shadered.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.ByteBuffer;

@Mixin(BufferBuilder.class)
public interface BufferBuilderAccessor {

    @Accessor(
            value = "buffer"
    )
    ByteBuffer shadered$getBuffer();

    @Accessor(
            value = "nextElementByte"
    )
    int shadered$getNextElementByte();
}
