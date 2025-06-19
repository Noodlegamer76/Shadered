#version 150

uniform sampler2D Skybox;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 skybox = texelFetch(Skybox, coord, 0);

    fragColor = skybox;
}
