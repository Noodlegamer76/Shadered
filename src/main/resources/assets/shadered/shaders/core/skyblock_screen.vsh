#version 150

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 texCoord0;
out float viewDist;

void main() {
    vec4 viewPos = ModelViewMat * vec4(Position, 1.0);

    viewDist = length(viewPos.xyz);

    gl_Position = ProjMat * viewPos;

    texCoord0 = UV0;
}