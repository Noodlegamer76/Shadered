#version 150

uniform sampler2D Color;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 invert = texelFetch(Color, coord, 0);

    fragColor = vec4(1.0) - invert;
    fragColor.a = 0.5;
}