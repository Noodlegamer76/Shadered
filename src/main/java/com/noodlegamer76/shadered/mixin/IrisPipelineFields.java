package com.noodlegamer76.shadered.mixin;

import net.irisshaders.iris.pipeline.CustomTextureManager;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.targets.RenderTargets;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IrisRenderingPipeline.class)
public class IrisPipelineFields {

    @Final
    @Shadow(remap = false)
    private CustomTextureManager customTextureManager;

    @Final
    @Shadow(remap = false)
    private RenderTargets renderTargets;
}
