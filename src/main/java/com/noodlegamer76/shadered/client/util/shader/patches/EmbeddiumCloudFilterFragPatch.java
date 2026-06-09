package com.noodlegamer76.shadered.client.util.shader.patches;

import com.noodlegamer76.shadered.client.util.shader.ShaderContext;
import com.noodlegamer76.shadered.client.util.shader.ShaderKeywords;
import com.noodlegamer76.shadered.client.util.shader.ShaderPatch;

public class EmbeddiumCloudFilterFragPatch implements ShaderPatch {

    @Override
    public String apply(String source, ShaderContext context) {
        if (!context.isFragment()) return source;

        if (source.contains("shadered_cloud_filter")) return source;

        // inject uniforms
        if (!source.contains("uniform sampler2D FilterSampler;")) {
            source = ShaderKeywords.injectAfterVersion(source, """
                    
                    uniform sampler2D FilterSampler;
                    
                    """);
        }

        String inject = """
                    
                        vec2 shadered_uv = gl_FragCoord.xy;
                        vec4 shadered_data = texture(FilterSampler, shadered_uv / vec2(textureSize(FilterSampler, 0)));
                    
                        int shadered_effect = int(shadered_data.r * 255.0 + 0.5);
                    
                        vec3 c = color.rgb;
                    
                        switch (shadered_effect) {
                    
                            case 1: break; // NORMAL
                    
                            case 2:
                                c = 1.0 - c;
                                break;
                    
                            case 3:
                                c = floor(c * 4.0) / 4.0;
                                break;
                    
                            case 4:
                            {
                                float g = dot(c, vec3(0.299, 0.587, 0.114));
                                c = vec3(g);
                                break;
                            }
                    
                            case 5: // CHROMATIC ABERRATION (cloud-safe fallback)
                            {
                                float shift = 0.002;
                                c.r = c.r;
                                c.g = c.g;
                                c.b = c.b;
                                break;
                            }
                    
                            case 6: // SCREEN (fallback pixelation)
                            {
                                float block = 64.0;
                                float v = floor(gl_FragCoord.x / block) * block;
                                float h = floor(gl_FragCoord.y / block) * block;
                                float t = sin(v + h) * 0.01;
                                c += vec3(t);
                                break;
                            }
                    
                            case 7:
                            {
                                float g = dot(c, vec3(0.299, 0.587, 0.114));
                                c = mix(vec3(0.1, 0.3, 0.6), vec3(0.8, 0.9, 1.0), g);
                                break;
                            }
                    
                            case 8:
                            {
                                float g = dot(c, vec3(0.299, 0.587, 0.114));
                                float d = fract(sin(gl_FragCoord.x * 12.9898 + gl_FragCoord.y * 78.233) * 43758.5453);
                                c = mix(vec3(0.06, 0.22, 0.06), vec3(0.60, 0.74, 0.38), step(d, g));
                                break;
                            }
                    
                            case 9:
                                break;
                    
                            default:
                                break;
                        }
                    
                        color.rgb = c;
                    
                    """;

        int idx = source.indexOf("fragColor =");
        if (idx != -1) {
            source = source.substring(0, idx)
                    + inject
                    + source.substring(idx);
        }

        return source;
    }
}