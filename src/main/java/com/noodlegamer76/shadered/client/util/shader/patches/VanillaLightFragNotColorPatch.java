package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderKeywords;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class VanillaLightFragNotColorPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (!context.isFragment()) return source;

        String uniforms = """

uniform vec3 u_LightPos[32];
uniform vec3 u_LightColor[32];
uniform float u_LightRadius[32];
uniform int u_LightCount;

in vec3 v_WorldPos;

""";

        String lightCode = """

    vec4 litColor = vertexColor;
    vec3 pointLighting = vec3(0.0);
    for (int i = 0; i < u_LightCount; i++) {
        vec3 toLight = u_LightPos[i] - v_WorldPos;
        float dist = length(toLight);
        float att = clamp(1.0 - (dist * dist) / (u_LightRadius[i] * u_LightRadius[i]), 0.0, 1.0);
        pointLighting += u_LightColor[i] * (att * att);
    }
    litColor.rgb += vertexColor.rgb * pointLighting;

""";

        source = ShaderKeywords.injectAfterVersion(source, uniforms);

        source = source.replace(
                "fragColor = linear_fog(vertexColor,",
                lightCode + "    fragColor = linear_fog(litColor,"
        );

        return source;
    }
}