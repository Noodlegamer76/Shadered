#version 430

uniform sampler2D diffuse;
uniform sampler2D normal;
uniform sampler2D pbr;
uniform sampler2D emissive;
uniform sampler2D height;
uniform vec3 u_LightPos[32];
uniform vec3 u_LightColor[32];
uniform float u_LightRadius[32];
uniform int u_LightCount;

uniform vec3 CameraPos;
uniform vec4 ColorModulator;
uniform vec3 LightDirection;
uniform vec3 LightColor;
uniform float Exposure;
uniform float AmbientStrength;

in vec3 v_WorldPos;

in vec2 texCoord0;
in vec4 vertexColor;
in vec3 vWorldPos;
in vec3 vNormal;
in vec4 vTangent;

out vec4 fragColor;

void main() {
    vec4 sampledAlbedo = texture(diffuse, texCoord0) * ColorModulator * vertexColor;
    if (sampledAlbedo.a < 0.1) discard;

    vec3 pointLighting = vec3(0.0);
    for (int i = 0; i < u_LightCount; i++) {
        vec3 toLight = u_LightPos[i] - v_WorldPos;
        float dist = length(toLight);
        float att = clamp(1.0 - (dist * dist) / (u_LightRadius[i] * u_LightRadius[i]), 0.0, 1.0);
        pointLighting += u_LightColor[i] * (att * att);
    }
    sampledAlbedo.rgb += texture(diffuse, texCoord0).rgb * ColorModulator.rgb * pointLighting;

    fragColor = sampledAlbedo;
}