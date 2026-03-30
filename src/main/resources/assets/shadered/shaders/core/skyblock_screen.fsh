#version 150

in vec2 texCoord0;
in float viewDist;

uniform sampler2D Skybox;
uniform sampler2D Pixel;
uniform vec2 ScreenSize;

out vec4 fragColor;

void main() {
    ivec2 screenCoord = ivec2(gl_FragCoord.xy);
    vec4 baseColor = texelFetch(Skybox, screenCoord, 0);

    const float minDist = 0.7;
    const float maxDist = 2.4;
    const float blockSize = 64.0;
    const float pixelBrightnessMultiplier = 1.0;

    vec2 grid = texCoord0 * blockSize;
    vec2 cellCenter = floor(grid) + 0.5;
    vec2 uvDiff = cellCenter - grid;

    vec2 dGdx = dFdx(grid);
    vec2 dGdy = dFdy(grid);
    float det = dGdx.x * dGdy.y - dGdx.y * dGdy.x;

    vec2 screenOffset = vec2(0.0);
    if (abs(det) > 0.00001) {
        screenOffset.x = (uvDiff.x * dGdy.y - uvDiff.y * dGdy.x) / det;
        screenOffset.y = (uvDiff.y * dGdx.x - uvDiff.x * dGdx.y) / det;
    }

    vec2 rawSnapped = gl_FragCoord.xy + screenOffset;
    ivec2 clampedCoord = ivec2(clamp(rawSnapped, vec2(0.0), ScreenSize - 1.0));

    vec4 pixelatedSky = texelFetch(Skybox, clampedCoord, 0);

    vec2 pixelUV = fract(grid);
    vec4 pixelMask = clamp(texture(Pixel, pixelUV) * pixelBrightnessMultiplier, 0.0, 1.0);

    vec4 combined = pixelMask * pixelatedSky;
    float t = clamp((maxDist - viewDist) / (maxDist - minDist), 0.0, 1.0);

    fragColor = mix(baseColor, combined, t);
}