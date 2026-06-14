package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class EmbeddiumFilterFragPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (!context.isFragment()) return source;

        if (!source.contains("uniform sampler2D FilterSampler;")) {
            source = injectAfterVersion(source, """

uniform sampler2D FilterSampler;
uniform sampler2D FilterDepthSampler;

""");
        }

        if (source.contains("shadered_EmbeddiumShouldApplyFilter")) {
            return source;
        }

        String filterCode = """

    vec2 shadered_FilterSize = vec2(textureSize(FilterSampler, 0));
    vec2 shadered_FilterUV = gl_FragCoord.xy / shadered_FilterSize;

    vec4 shadered_FilterData = texture(FilterSampler, shadered_FilterUV);

    float shadered_CurrentDepth = gl_FragCoord.z;
    float shadered_FilterDepth = texture(FilterDepthSampler, shadered_FilterUV).r;

    bool shadered_EmbeddiumShouldApplyFilter =
        shadered_CurrentDepth > shadered_FilterDepth + 0.000001;

    if (shadered_EmbeddiumShouldApplyFilter) {
        int shadered_FilterEffect = int(shadered_FilterData.r * 255.0 + 0.5);

        switch (shadered_FilterEffect) {
            case 1: { // NORMAL
                break;
            }

            case 2: { // INVERTED
                diffuseColor.rgb = 1.0 - diffuseColor.rgb;
                break;
            }

            case 3: { // POSTERIZE
                float levels = 4.0;
                diffuseColor.rgb = floor(diffuseColor.rgb * levels) / levels;
                break;
            }

            case 4: { // GRAYSCALE
                float g = dot(diffuseColor.rgb, vec3(0.299, 0.587, 0.114));
                diffuseColor.rgb = (vec3(g) - 0.5) * 1.3 + 0.5;
                break;
            }

            case 5: { // CHROMATIC_ABERRATION
                vec2 size = vec2(textureSize(u_BlockTex, 0));
                float offset = 4.0 / size.x;

                float r = texture(u_BlockTex, v_TexCoord + vec2(offset, 0.0), v_MaterialMipBias).r;
                float g = texture(u_BlockTex, v_TexCoord, v_MaterialMipBias).g;
                float b = texture(u_BlockTex, v_TexCoord - vec2(offset, 0.0), v_MaterialMipBias).b;

                diffuseColor.rgb = vec3(r, g, b) * v_Color.rgb;
                break;
            }

            case 6: { // SCREEN
                float blockSize = 64.0;
                vec2 size = vec2(textureSize(u_BlockTex, 0));
                vec2 pixelUV = (floor(v_TexCoord * size / blockSize) * blockSize + (blockSize * 0.5)) / size;

                diffuseColor.rgb = texture(u_BlockTex, pixelUV, v_MaterialMipBias).rgb * v_Color.rgb;
                break;
            }

            case 7: { // BLUEPRINT
                vec2 size = vec2(textureSize(u_BlockTex, 0));
                vec2 pixel = 1.0 / size;

                float l = dot(diffuseColor.rgb, vec3(0.299, 0.587, 0.114));
                float lUp = dot(texture(u_BlockTex, v_TexCoord + vec2(0.0, pixel.y), v_MaterialMipBias).rgb, vec3(0.299, 0.587, 0.114));
                float lDown = dot(texture(u_BlockTex, v_TexCoord - vec2(0.0, pixel.y), v_MaterialMipBias).rgb, vec3(0.299, 0.587, 0.114));
                float lLeft = dot(texture(u_BlockTex, v_TexCoord - vec2(pixel.x, 0.0), v_MaterialMipBias).rgb, vec3(0.299, 0.587, 0.114));
                float lRight = dot(texture(u_BlockTex, v_TexCoord + vec2(pixel.x, 0.0), v_MaterialMipBias).rgb, vec3(0.299, 0.587, 0.114));

                float edge = abs(l - lUp) + abs(l - lDown) + abs(l - lLeft) + abs(l - lRight);
                edge = smoothstep(0.02, 0.1, edge);

                diffuseColor.rgb = mix(vec3(0.1, 0.3, 0.6), vec3(0.8, 0.9, 1.0), edge);
                break;
            }

            case 8: { // GAMEBOY
                ivec2 coord = ivec2(gl_FragCoord.xy);
                float g = dot(diffuseColor.rgb, vec3(0.299, 0.587, 0.114));

                mat4 threshold = mat4(
                    1.0, 9.0, 3.0, 11.0,
                    13.0, 5.0, 15.0, 7.0,
                    4.0, 12.0, 2.0, 10.0,
                    16.0, 8.0, 14.0, 6.0
                ) / 17.0;

                float dither = threshold[coord.x % 4][coord.y % 4];
                float outputColor = step(dither, g);

                diffuseColor.rgb = mix(vec3(0.06, 0.22, 0.06), vec3(0.60, 0.74, 0.38), outputColor);
                break;
            }

            case 9: { // BACKGROUND
                break;
            }

            default:
                break;
        }
    }

""";

       int fogIdx = source.indexOf("fragColor = _linearFog(");
       if (fogIdx != -1) {
           source = source.substring(0, fogIdx)
                   + filterCode
                   + source.substring(fogIdx);
       }

        return source;
    }

    private String injectAfterVersion(String source, String inject) {
        if (!source.contains("#version")) return inject + source;

        int idx = source.indexOf("\n", source.indexOf("#version"));
        return source.substring(0, idx + 1) + inject + source.substring(idx + 1);
    }
}