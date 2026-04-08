#version 150

uniform sampler2D Glass;
uniform sampler2D PortalDepth;
uniform sampler2D SceneDepth;
uniform sampler2D MainColor;

out vec4 fragColor;

void main() {
    ivec2 coord = ivec2(gl_FragCoord.xy);
    vec4 color = texelFetch(Glass, coord, 0);

    float sceneDepth = texelFetch(SceneDepth, coord, 0).r;
    float portalDepth = gl_FragCoord.z;

   //if (sceneDepth > portalDepth) {
   //    color = texelFetch(MainColor, coord, 0);
   //}

    fragColor = color;
}
