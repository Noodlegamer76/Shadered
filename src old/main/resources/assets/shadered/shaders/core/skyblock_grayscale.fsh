#version 150

uniform sampler2D Skybox;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 skybox = texelFetch(Skybox, coord, 0);

    float g = dot(skybox.rgb, vec3(0.299, 0.587, 0.114));
    vec3 color = vec3(g);

    color = (color - 0.5) * 1.3 + 0.5;

    fragColor = vec4(color, skybox.a);
}