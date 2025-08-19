#version 150

uniform sampler2D Color;
uniform sampler2D Manifold;
uniform sampler2D Grainy2;
uniform vec2 ScreenSize;
uniform float GameTime;

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec2 coord = gl_FragCoord.xy / ScreenSize;

    vec4 manifold = texture(Manifold, texCoord0 * 2.75 + (GameTime * 20));
    vec4 grainy = texture(Grainy2, texCoord0 * 1.55 + (-GameTime * 64));

    vec2 displacement = (manifold.rg + grainy.rg - 1.0) * 0.05;

    vec4 color = texture(Color, coord + displacement);

    fragColor = color;
}
