package com.noodlegamer76.shadered.mixin;

import net.irisshaders.iris.pipeline.CustomTextureManager;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.targets.RenderTargets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = IrisRenderingPipeline.class, remap = false)
public interface IrisPipelineAccessor {

    @Accessor(value = "customTextureManager", remap = false)
    CustomTextureManager getCustomTextureManager();

    @Accessor(value = "renderTargets", remap = false)
    RenderTargets getRenderTargets();
}
