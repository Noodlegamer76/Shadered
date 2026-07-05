#version 150

uniform sampler2D MainDepth;
uniform sampler2D MainColor;
uniform sampler2D BallTexture;
uniform sampler2D SpaceTexture;

uniform mat4 ProjMat;
uniform mat4 ViewMat;
uniform vec3 CameraPos;
uniform ivec3 CameraPosInteger;
uniform float GameTime;
uniform vec4 FogColor;
uniform vec2 ScreenSize;

uniform float DensityMultiplier;
uniform int Steps;
uniform float Amplitude;
uniform float Frequency;

out vec4 fragColor;

float interleavedGradientNoise(vec2 uv) {
    vec3 magic = vec3(0.06711056, 0.00583715, 52.9829189);
    return fract(magic.z * fract(dot(uv, magic.xy)));
}

float hash(vec3 p) {
    p = fract(p * 0.1031);
    p += dot(p, p.zyx + 31.32);
    return fract((p.x + p.y) * p.z);
}

float noise(vec3 x) {
    vec3 i = floor(x);
    vec3 f = fract(x);
    vec3 u = f * f * (3.0 - 2.0 * f);

    return mix(mix(mix(hash(i + vec3(0.0, 0.0, 0.0)), hash(i + vec3(1.0, 0.0, 0.0)), u.x),
                   mix(hash(i + vec3(0.0, 1.0, 0.0)), hash(i + vec3(1.0, 1.0, 0.0)), u.x), u.y),
               mix(mix(hash(i + vec3(0.0, 0.0, 1.0)), hash(i + vec3(1.0, 0.0, 1.0)), u.x),
                   mix(hash(i + vec3(0.0, 1.0, 1.0)), hash(i + vec3(1.0, 1.0, 1.0)), u.x), u.y), u.z);
}

float fbm(vec3 p) {
    float value = 0.0;
    float currentAmplitude = Amplitude;
    float currentFrequency = Frequency;

    for (int i = 0; i < 3; i++) {
        value += currentAmplitude * noise(p * currentFrequency);
        currentFrequency *= 2.0;
        currentAmplitude *= 0.5;
    }
    return value;
}

float fogDensity(vec3 worldPos) {
    vec3 animatedPos = worldPos + vec3(GameTime * 0.15, GameTime * -0.05, GameTime * 0.08);

    float waves = sin(animatedPos.x * 0.02) *
    sin(animatedPos.y * 0.015) *
    sin(animatedPos.z * 0.02);
    waves = waves * 0.5 + 0.5;

    float details = fbm(animatedPos * 0.03);
    float finalFog = waves * (details * 0.7 + 0.3);

    finalFog = smoothstep(0.1, 0.8, finalFog);

    return max(0.0, finalFog);
}

float sphere(vec3 pos, vec3 center, float radius) {
    return length(pos - center) - radius;
}

float sdMandelbulb(vec3 p, float power, int iterations) {
    vec3 z = p;
    float dr = 1.0;
    float r = length(z);

    for (int i = 0; i < iterations; i++) {
        float theta = acos(z.z / r);
        float phi = atan(z.y, z.x);

        float zr = pow(r, power - 1.0);
        dr = zr * power * dr + 1.0;

        zr = pow(r, power);
        theta = theta * power;
        phi = phi * power;

        z = zr * vec3(sin(theta) * cos(phi),
        sin(theta) * sin(phi),
        cos(theta));
        z += p;

        r = length(z);
        if (r > 2.0) break;
    }

    return 0.5 * log(r) * r / dr;
}

void main() {
    ivec2 pixel = ivec2(gl_FragCoord.xy);
    vec2 screenSize = vec2(textureSize(MainDepth, 0));

    vec2 uv = gl_FragCoord.xy / screenSize;
    vec2 ndc = uv * 2.0 - 1.0;

    vec4 clip = vec4(ndc, 1.0, 1.0);
    vec4 view = inverse(ProjMat) * clip;
    view /= view.w;

    vec3 rayDirView = normalize(view.xyz);
    mat3 invViewRot = transpose(mat3(ViewMat));
    vec3 rayDir = normalize(invViewRot * rayDirView);

    float sceneDepth = texelFetch(MainDepth, pixel, 0).r;
    float maxDistance = 128.0;

    if (sceneDepth < 1.0) {
        vec4 sceneClip = vec4(ndc, sceneDepth * 2.0 - 1.0, 1.0);
        vec4 sceneView = inverse(ProjMat) * sceneClip;
        sceneView /= sceneView.w;
        maxDistance = length(sceneView.xyz);
    }

    vec3 ballColor = texelFetch(BallTexture, pixel, 0).rgb;
    vec3 spaceColor = texelFetch(SpaceTexture, pixel, 0).rgb;
    vec3 sceneColor = texelFetch(MainColor, pixel, 0).rgb;
    vec3 worldCameraPos = CameraPos;

    float transmittance = 1.0;
    vec3 accumulatedFog = vec3(0.0);

    float stepSize = maxDistance / float(Steps);

    float dither = interleavedGradientNoise(gl_FragCoord.xy);
    float currentDist = dither * stepSize;

    for (int i = 0; i < Steps; i++) {
        if (currentDist >= maxDistance) {
            break;
        }

        vec3 worldPos = worldCameraPos + rayDir * currentDist;

        float density = fogDensity(worldPos) * DensityMultiplier;
        float absorb = density * stepSize;

        accumulatedFog += FogColor.rgb * density * transmittance * stepSize;
        transmittance *= exp(-absorb);

        currentDist += stepSize;

        if (transmittance < 0.01) {
            transmittance = 0.0;
            break;
        }
    }

    vec3 finalColor = (sceneColor * transmittance) + accumulatedFog;
    fragColor = vec4(finalColor, 1.0);
}