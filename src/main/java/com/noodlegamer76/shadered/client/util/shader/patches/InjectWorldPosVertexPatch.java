package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class InjectWorldPosVertexPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (context.isFragment()) return source;

        if (!source.contains("out vec3 v_WorldPos;")) {
            source = source.replace(
                    "out vec2 v_TexCoord;",
                    "out vec2 v_TexCoord;\nout vec3 v_WorldPos;"
            );
        }

        if (source.contains("vec3 position = _vert_position + translation;")) {
            source = source.replace(
                    "vec3 position = _vert_position + translation;",
                    "vec3 position = _vert_position + translation;\n    v_WorldPos = position;"
            );
        }

        return source;
    }
}