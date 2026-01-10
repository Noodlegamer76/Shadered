#version 150

uniform sampler2D Sampler0;
uniform sampler2D Noise;
uniform float GameTime;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec4 noise = texture(Noise, texCoord0 + GameTime * 3.0);

    vec2 toCenter = vec2(0.5) - texCoord0;
    vec2 offset = toCenter * noise.r * 0.075 ;

    vec2 uv = texCoord0 + offset;

    vec4 color = texture(Sampler0, uv);

    if (color.a == 0.0) {
        discard;
    }

    fragColor = color * vertexColor;
}
