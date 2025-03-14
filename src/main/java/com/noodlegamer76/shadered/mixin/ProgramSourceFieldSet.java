package com.noodlegamer76.shadered.mixin;

import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProgramSource.class)
public class ProgramSourceFieldSet {

    @Mutable
    @Final
    @Shadow(remap = false)
    private String fragmentSource;
}
