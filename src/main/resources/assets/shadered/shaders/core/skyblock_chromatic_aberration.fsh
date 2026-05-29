#version 150

uniform sampler2D Skybox;

out vec4 fragColor;

void main() {
    vec2 size = vec2(textureSize(Skybox, 0));
    vec2 uv = gl_FragCoord.xy / size;

    float offset = 4.0 / size.x;

    float r = texture(Skybox, uv + vec2(offset, 0.0)).r;
    float g = texture(Skybox, uv).g;
    float b = texture(Skybox, uv - vec2(offset, 0.0)).b;

    fragColor = vec4(r, g, b, 1.0);
}