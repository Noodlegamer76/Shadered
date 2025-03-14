package com.noodlegamer76.shadered.mixin;

import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ProgramSource.class)
public interface ProgramSourceAccessor {

    @Accessor("fragmentSource") // This exposes the field
    void setFragmentSource(String source); // This acts as a setter
}
