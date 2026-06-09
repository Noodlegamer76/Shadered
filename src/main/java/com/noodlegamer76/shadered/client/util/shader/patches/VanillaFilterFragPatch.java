package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderKeywords;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class VanillaFilterFragPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (!context.isFragment()) return source;

        if (!source.contains("uniform sampler2D FilterSampler;")) {
            source = ShaderKeywords.injectAfterVersion(source, """

uniform sampler2D FilterSampler;

""");
        }

        String filterCode = """

    vec2 shadered_FilterUV = gl_FragCoord.xy;
    vec4 shadered_FilterData = texture(FilterSampler, shadered_FilterUV);
    int shadered_FilterEffect = int(shadered_FilterData.r * 255.0 + 0.5);

    switch (shadered_FilterEffect) {
        case 1: { // NORMAL
            break;
        }

        case 2: { // INVERTED
            color.rgb = 1.0 - color.rgb;
            break;
        }

        case 3: { // POSTERIZE
            float levels = 4.0;
            color.rgb = floor(color.rgb * levels) / levels;
            break;
        }

        case 4: { // GRAYSCALE
            float g = dot(color.rgb, vec3(0.299, 0.587, 0.114));
            color.rgb = (vec3(g) - 0.5) * 1.3 + 0.5;
            break;
        }

        case 5: { // CHROMATIC_ABERRATION
            vec2 size = vec2(textureSize(Sampler0, 0));
            float offset = 4.0 / size.x;
            float r = texture(Sampler0, texCoord0 + vec2(offset, 0.0)).r;
            float g = texture(Sampler0, texCoord0).g;
            float b = texture(Sampler0, texCoord0 - vec2(offset, 0.0)).b;
            color.rgb = vec3(r, g, b) * ColorModulator.rgb;
            break;
        }

        case 6: { // SCREEN
            float blockSize = 64.0;
            vec2 size = vec2(textureSize(Sampler0, 0));
            vec2 pixelUV = (floor(texCoord0 * size / blockSize) * blockSize + (blockSize * 0.5)) / size;
            color.rgb = texture(Sampler0, pixelUV).rgb * ColorModulator.rgb;
            break;
        }

            case 7: { // BLUEPRINT
                vec2 size = vec2(textureSize(Sampler0, 0));
                vec2 pixel = 1.0 / size;

                float l = dot(color.rgb, vec3(0.299, 0.587, 0.114));
                float lUp = dot(texture(Sampler0, texCoord0 + vec2(0.0, pixel.y)).rgb, vec3(0.299, 0.587, 0.114));
                float lDown = dot(texture(Sampler0, texCoord0 - vec2(0.0, pixel.y)).rgb, vec3(0.299, 0.587, 0.114));
                float lLeft = dot(texture(Sampler0, texCoord0 - vec2(pixel.x, 0.0)).rgb, vec3(0.299, 0.587, 0.114));
                float lRight = dot(texture(Sampler0, texCoord0 + vec2(pixel.x, 0.0)).rgb, vec3(0.299, 0.587, 0.114));

                float edge = abs(l - lUp) + abs(l - lDown) + abs(l - lLeft) + abs(l - lRight);
                edge = smoothstep(0.02, 0.1, edge);

                color.rgb = mix(vec3(0.1, 0.3, 0.6), vec3(0.8, 0.9, 1.0), edge);
                break;
            }

        case 8: { // GAMEBOY
            ivec2 coord = ivec2(gl_FragCoord.xy);
            float g = dot(color.rgb, vec3(0.299, 0.587, 0.114));

            mat4 threshold = mat4(
                1.0, 9.0, 3.0, 11.0,
                13.0, 5.0, 15.0, 7.0,
                4.0, 12.0, 2.0, 10.0,
                16.0, 8.0, 14.0, 6.0
            ) / 17.0;

            float dither = threshold[coord.x % 4][coord.y % 4];
            float outputColor = step(dither, g);
            color.rgb = mix(vec3(0.06, 0.22, 0.06), vec3(0.60, 0.74, 0.38), outputColor);
            break;
        }

        case 9: { // BACKGROUND
            break;
        }

        default:
            break;
    }

""";

      source = source.replace(
              "fragColor = linear_fog(",
              filterCode + "    fragColor = linear_fog("
      );

        return source;
    }
}