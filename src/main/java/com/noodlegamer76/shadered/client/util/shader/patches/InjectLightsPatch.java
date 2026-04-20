package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderKeywords;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class InjectLightsPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (!context.isFragment()) return source;

        if (!source.contains("in vec3 v_WorldPos;")) {
            source = source.replace(
                    "in vec2 v_TexCoord;",
                    "in vec2 v_TexCoord;\nin vec3 v_WorldPos;"
            );
        }

        String uniforms = """

    uniform vec3 u_LightPos[32];
    uniform vec3 u_LightColor[32];
    uniform float u_LightRadius[32];
    uniform int u_LightCount;

    """;

        if (!source.contains("u_LightCount")) {
            source = injectAfterVersion(source, uniforms);
        }

        String lightCode = """
        
            vec3 pointLighting = vec3(0.0);
            for (int i = 0; i < u_LightCount; i++) {
                vec3 toLight = u_LightPos[i] - v_WorldPos;
                float dist = length(toLight);
                float att = clamp(1.0 - (dist * dist) / (u_LightRadius[i] * u_LightRadius[i]), 0.0, 1.0);
                pointLighting += u_LightColor[i] * (att * att);
            }
            
            diffuseColor.rgb += (texture(u_BlockTex, v_TexCoord, v_MaterialMipBias).rgb * pointLighting);
        """;

        String vanillaTarget = "diffuseColor *= v_Color;";
        int vanillaIdx = source.indexOf(vanillaTarget);

        if (vanillaIdx != -1) {
            int insert = vanillaIdx + vanillaTarget.length();
            source = source.substring(0, insert)
                    + "\n" + lightCode
                    + source.substring(insert);
        }

        String normalTarget = "diffuseColor.rgb *= v_Color.rgb;";
        int normalIdx = source.indexOf(normalTarget);

        if (normalIdx != -1) {
            int insert = normalIdx + normalTarget.length();
            source = source.substring(0, insert)
                    + "\n" + lightCode
                    + source.substring(insert);
        }

        if (vanillaIdx == -1 && normalIdx == -1) {
            int fogIdx = source.indexOf("fragColor = _linearFog(");
            if (fogIdx != -1) {
                source = source.substring(0, fogIdx)
                        + lightCode
                        + source.substring(fogIdx);
            }
        }

        return source;
    }

    private String injectAfterVersion(String source, String inject) {
        if (!source.contains("#version")) return inject + source;
        int idx = source.indexOf("\n", source.indexOf("#version"));
        return source.substring(0, idx + 1) + inject + source.substring(idx + 1);
    }
}