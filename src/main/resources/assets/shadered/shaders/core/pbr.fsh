#version 430

uniform sampler2D diffuse;
uniform sampler2D normal;
uniform sampler2D pbr;
uniform sampler2D emissive;

uniform vec3 CameraPos;
uniform vec4 ColorModulator;
uniform vec3 LightDirection;
uniform vec3 LightColor;
uniform float Exposure;
uniform float AmbientStrength;

in vec2 texCoord0;
in vec4 vertexColor;
in vec3 vWorldPos;
in vec3 vNormal;
in vec4 vTangent;

out vec4 fragColor;

void main() {
    vec4 sampledAlbedo = texture(diffuse, texCoord0) * ColorModulator * vertexColor;
    if (sampledAlbedo.a < 0.1) discard;

    fragColor = sampledAlbedo;
}