#version 150

uniform sampler2D Skybox;

out vec4 fragColor;

void main() {
    ivec2 c = ivec2(gl_FragCoord.xy);
    
    float l = dot(texelFetch(Skybox, c, 0).rgb, vec3(0.299, 0.587, 0.114));
    
    float l_up    = dot(texelFetch(Skybox, c + ivec2(0, 1), 0).rgb, vec3(0.299, 0.587, 0.114));
    float l_down  = dot(texelFetch(Skybox, c + ivec2(0, -1), 0).rgb, vec3(0.299, 0.587, 0.114));
    float l_left  = dot(texelFetch(Skybox, c + ivec2(-1, 0), 0).rgb, vec3(0.299, 0.587, 0.114));
    float l_right = dot(texelFetch(Skybox, c + ivec2(1, 0), 0).rgb, vec3(0.299, 0.587, 0.114));
    
    float edge = abs(l - l_up) + abs(l - l_down) + abs(l - l_left) + abs(l - l_right);
    edge = smoothstep(0.02, 0.1, edge); // Threshold the edge visibility

    vec3 paperColor = vec3(0.1, 0.3, 0.6);
    vec3 lineColor = vec3(0.8, 0.9, 1.0);
    
    vec3 final = mix(paperColor, lineColor, edge);
    
    fragColor = vec4(final, 1.0);
}