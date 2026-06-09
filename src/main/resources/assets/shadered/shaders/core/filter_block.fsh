#version 150

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    fragColor = vec4(
        vertexColor.r,
        0.0,
        0.0,
        1.0
    );
}