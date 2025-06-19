#version 150

uniform sampler2D EntityDepth;
uniform sampler2D CloudDepth;
uniform sampler2D ItemEntityDepth;
uniform sampler2D TranslucentDepth;
uniform sampler2D WeatherDepth;
uniform sampler2D ParticlesDepth;
uniform vec2 ScreenSize;

void main() {
    vec2 coord = gl_FragCoord.xy / ScreenSize;

    float entity = texture(EntityDepth, coord).r;
    float cloud = texture(CloudDepth, coord).r;
    float itemEntity = texture(ItemEntityDepth, coord).r;
    float translucent = texture(TranslucentDepth, coord).r;
    float weather = texture(WeatherDepth, coord).r;
    float particles = texture(ParticlesDepth, coord).r;

    float minDepth = min(entity, min(cloud, min(itemEntity, min(translucent, min(weather, particles)))));

    //float minDepth = min(cloud, min(translucent, particles));

    gl_FragDepth = minDepth;
}