#version 150

uniform sampler2D Skybox;
uniform sampler2D MainDepth;
uniform sampler2D PassDepth;

out vec4 fragColor;

in vec2 texCoord0;
in vec4 vertexColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 skybox = texelFetch(Skybox, coord, 0);

    vec4 mainDepth = texelFetch(MainDepth, coord, 0);
    vec4 passDepth = texelFetch(PassDepth, coord, 0);

    if (mainDepth.r < 1.0 || passDepth.r < 1.0) {
        discard;
    }

    fragColor = skybox * vertexColor;
}
