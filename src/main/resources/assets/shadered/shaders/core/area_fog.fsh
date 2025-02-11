#version 150

uniform sampler2D Depth;
uniform mat4 ProjMat;
uniform mat4 ModelView2;
uniform vec2 ScreenSize;
uniform vec3 CameraPos;
uniform vec3 FogPos;

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

    vec3 finalFogPos = worldPos - FogPos;

    float distanceFromCamera = length(eyePlayerPos);
    float distanceFromWorld = length(finalFogPos);

    float worldFogMinimum = 150.;
    float worldFogMaximum = 100.;

    float playerFogMinimum = 0.;
    float playerFogMaximum = 10.;

    float fogStrength = float(max(min((distanceFromWorld - worldFogMinimum) / (worldFogMaximum - worldFogMinimum), 1.0), 0));

    fogStrength *= float(max(min((distanceFromCamera - playerFogMinimum) / (playerFogMaximum - playerFogMinimum), 1.0), 0));

    fragColor = vec4(0.75, 0.75, 0.75, fogStrength);
}
