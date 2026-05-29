#version 430

in vec3 Position;
in vec2 UV0;
in vec4 Color;
in vec3 Normal;
in vec4 Tangent;

in vec4 BoneIDs;
in vec4 BoneWeights;

uniform mat4 ModelMat;
uniform mat4 ViewMat;
uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform int Offset;

layout(std430, binding = 0) buffer Bones {
    mat4 BoneMatrices[];
};

out vec2 texCoord0;
out vec4 vertexColor;

out vec3 vWorldPos;
out vec3 vNormal;
out vec4 vTangent;
out vec3 v_WorldPos;

mat4 getSkinMatrix() {
    if (Offset < 0) {
        return mat4(1.0);
    }

    float weightSum = BoneWeights.x + BoneWeights.y +
                      BoneWeights.z + BoneWeights.w;

    if (weightSum < 0.1) {
        return mat4(1.0);
    }


    mat4 skin =
        BoneWeights.x * BoneMatrices[Offset + int(BoneIDs.x)] +
        BoneWeights.y * BoneMatrices[Offset + int(BoneIDs.y)] +
        BoneWeights.z * BoneMatrices[Offset + int(BoneIDs.z)] +
        BoneWeights.w * BoneMatrices[Offset + int(BoneIDs.w)];

    return skin;
}

void main() {
    mat4 skinMat = getSkinMatrix();

    vec4 skinnedPos = skinMat * vec4(Position, 1.0);
    vec3 skinnedNormal = mat3(skinMat) * Normal;
    vec4 skinnedTangent = vec4(mat3(skinMat) * Tangent.xyz, Tangent.w);

    vec4 worldPos = ModelMat * skinnedPos;
    vec4 viewPos = ViewMat * worldPos;

    gl_Position = ProjMat * viewPos;

    texCoord0 = UV0;
    vertexColor = Color;

    vWorldPos = worldPos.xyz;
    vNormal = normalize(mat3(ModelMat) * skinnedNormal);

    vTangent = vec4(normalize(mat3(ModelMat) * skinnedTangent.xyz), skinnedTangent.w);
    v_WorldPos = (ModelViewMat * vec4(Position, 1.0)).xyz;
}