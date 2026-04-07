#version 150

uniform sampler2D Skybox;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 tex = texelFetch(Skybox, coord, 0);
    float g = dot(tex.rgb, vec3(0.299, 0.587, 0.114));

    // 4x4 Bayer Dither Matrix
    mat4 threshold = mat4(
        1.0, 9.0, 3.0, 11.0,
        13.0, 5.0, 15.0, 7.0,
        4.0, 12.0, 2.0, 10.0,
        16.0, 8.0, 14.0, 6.0
    ) / 17.0;

    float dither = threshold[coord.x % 4][coord.y % 4];
    float outputColor = step(dither, g);

    vec3 gbGreen = mix(vec3(0.06, 0.22, 0.06), vec3(0.60, 0.74, 0.38), outputColor);

    fragColor = vec4(gbGreen, tex.a);
}