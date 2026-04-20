package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderKeywords;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class VanillaLightVertexPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (context.isFragment()) return source;

        source = ShaderKeywords.injectAfterVersion(source, """
out vec3 v_WorldPos;
""");

        source = ShaderKeywords.injectBeforeMainEnd(source,
                "v_WorldPos = (ModelViewMat * vec4(Position, 1.0)).xyz;"
        );

        return source;
    }
}