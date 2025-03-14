#version 150

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 skybox = texelFetch(Sampler0, coord, 0);

    fragColor = skybox;
}
