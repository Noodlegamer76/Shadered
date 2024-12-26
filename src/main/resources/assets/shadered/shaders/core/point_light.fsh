#version 150

uniform sampler2D Depth;
uniform vec4 LightColor;
uniform mat4 ProjMat;
uniform mat4 ModelView2;
uniform vec2 ScreenSize;
uniform vec3 CameraPos;
uniform vec3 LightPos;

out vec4 fragColor;

vec3 projectAndDivide(mat4 projectionMatrix, vec3 position) {
    vec4 homogeneousPos = projectionMatrix * vec4(position, 1.0);
    return homogeneousPos.xyz / homogeneousPos.w;
}

void main() {

    vec2 normCoords = gl_FragCoord.xy / ScreenSize;
    vec3 screenPos = vec3(normCoords, texture(Depth, normCoords));
    vec3 ndcPos = screenPos * 2.0 - 1.0;
    vec3 viewPos = projectAndDivide(inverse(ProjMat), ndcPos);
    vec3 eyePlayerPos = vec3(mat3(inverse(ModelView2)) * viewPos);
    vec3 eyeCameraPosition = CameraPos + inverse(ModelView2)[3].xyz;
    vec3 worldPos = eyePlayerPos + eyeCameraPosition;

    vec3 LightDir = LightPos - worldPos;
    float fragDist = length(LightDir);

    float attenuation = 1.0 / (1.0 + 0.1 * fragDist + 0.01 * fragDist * fragDist);

    fragColor = vec4(LightColor.rgb * attenuation, LightColor.a * attenuation);
}
