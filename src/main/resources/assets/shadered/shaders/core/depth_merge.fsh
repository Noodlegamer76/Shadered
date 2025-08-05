#version 150

uniform sampler2D Skybox;
uniform sampler2D SkyboxDepth;
uniform sampler2D MainDepth;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 skybox = texelFetch(Skybox, coord, 0);
    vec4 skyboxDepth = texelFetch(SkyboxDepth, coord, 0);
    vec4 mainDepth = texelFetch(MainDepth, coord, 0);



    fragColor = skybox;
}