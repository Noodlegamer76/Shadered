package com.noodlegamer76.shadered.client.util.shader;

public interface ShaderPatch {
    String apply(String source, ShaderContext context);
}