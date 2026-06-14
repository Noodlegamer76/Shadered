#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform sampler2D FilterSampler;
uniform sampler2D FilterDepthSampler;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 sceneColor = texture(DiffuseSampler, texCoord);
    vec4 filterData = texture(FilterSampler, texCoord);

    float sceneDepth = texture(DepthSampler, texCoord).r;
    float warpDepth = texture(FilterDepthSampler, texCoord).r;

    if (warpDepth >= 0.999999) {
        fragColor = sceneColor;
        return;
    }

    if (sceneDepth <= warpDepth) {
        fragColor = sceneColor;
        return;
    }

    int effect = int(filterData.r * 255.0 + 0.5);

    //temp disable
    effect = 0;

    vec2 uv = texCoord;
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 finalColor = sceneColor;

    switch (effect) {
        case 1: { // NORMAL
            finalColor = sceneColor;
            break;
        }

        case 2: { // INVERTED
            finalColor = vec4(1.0 - sceneColor.rgb, sceneColor.a);
            break;
        }

        case 3: { // POSTERIZE
            float levels = 4.0;
            finalColor = vec4(floor(sceneColor.rgb * levels) / levels, sceneColor.a);
            break;
        }

        case 4: { // GRAYSCALE
            float g = dot(sceneColor.rgb, vec3(0.299, 0.587, 0.114));
            vec3 color = vec3(g);
            color = (color - 0.5) * 1.3 + 0.5;
            finalColor = vec4(color, sceneColor.a);
            break;
        }

        case 5: { // CHROMATIC_ABERRATION
            vec2 size = vec2(textureSize(DiffuseSampler, 0));
            float offset = 4.0 / size.x;
            float r = texture(DiffuseSampler, uv + vec2(offset, 0.0)).r;
            float g = texture(DiffuseSampler, uv).g;
            float b = texture(DiffuseSampler, uv - vec2(offset, 0.0)).b;
            finalColor = vec4(r, g, b, sceneColor.a);
            break;
        }

        case 6: { // SCREEN
            // Note: The original screen pass relied on uniforms (Pixel texture, viewDist)
            // that aren't bound in FilterBlockComplexPass. This applies standard pixelation as a fallback.
            float blockSize = 64.0;
            vec2 size = vec2(textureSize(DiffuseSampler, 0));
            vec2 pixelUV = (floor(uv * size / blockSize) * blockSize + (blockSize * 0.5)) / size;
            finalColor = texture(DiffuseSampler, pixelUV);
            break;
        }

        case 7: { // BLUEPRINT
            float l = dot(texelFetch(DiffuseSampler, coord, 0).rgb, vec3(0.299, 0.587, 0.114));
            float l_up    = dot(texelFetch(DiffuseSampler, coord + ivec2(0, 1), 0).rgb, vec3(0.299, 0.587, 0.114));
            float l_down  = dot(texelFetch(DiffuseSampler, coord + ivec2(0, -1), 0).rgb, vec3(0.299, 0.587, 0.114));
            float l_left  = dot(texelFetch(DiffuseSampler, coord + ivec2(-1, 0), 0).rgb, vec3(0.299, 0.587, 0.114));
            float l_right = dot(texelFetch(DiffuseSampler, coord + ivec2(1, 0), 0).rgb, vec3(0.299, 0.587, 0.114));

            float edge = abs(l - l_up) + abs(l - l_down) + abs(l - l_left) + abs(l - l_right);
            edge = smoothstep(0.02, 0.1, edge);

            vec3 paperColor = vec3(0.1, 0.3, 0.6);
            vec3 lineColor = vec3(0.8, 0.9, 1.0);

            finalColor = vec4(mix(paperColor, lineColor, edge), sceneColor.a);
            break;
        }

        case 8: { // GAMEBOY
            float g = dot(sceneColor.rgb, vec3(0.299, 0.587, 0.114));

            mat4 threshold = mat4(
                1.0, 9.0, 3.0, 11.0,
                13.0, 5.0, 15.0, 7.0,
                4.0, 12.0, 2.0, 10.0,
                16.0, 8.0, 14.0, 6.0
            ) / 17.0;

            float dither = threshold[coord.x % 4][coord.y % 4];
            float outputColor = step(dither, g);

            vec3 gbGreen = mix(vec3(0.06, 0.22, 0.06), vec3(0.60, 0.74, 0.38), outputColor);
            finalColor = vec4(gbGreen, sceneColor.a);
            break;
        }

        case 9: { // BACKGROUND
            finalColor = sceneColor;
            break;
        }

        default:
            break;
    }

    fragColor = finalColor;
}