package com.noodlegamer76.shadered.client.util.shader;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.client.util.shader.patches.EmbeddiumFilterFragPatch;
import com.noodlegamer76.shadered.client.util.shader.patches.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderPatchRegistry {
    private static final Map<String, List<ShaderPatch>> PATCHES = new HashMap<>();

    static {
        ShaderPatchRegistry.register(
                "blocks/block_layer_opaque.fsh",
                new InjectLightsPatch()
        );
        ShaderPatchRegistry.register(
                "blocks/block_layer_opaque.fsh",
                new EmbeddiumFilterFragPatch()
        );
        ShaderPatchRegistry.register(
                "clouds",
                new EmbeddiumCloudFilterFragPatch()
        );

        ShaderPatchRegistry.register(
                "blocks/block_layer_opaque.vsh",
                new InjectWorldPosVertexPatch()
        );

        registerVanillaLitFiltered("rendertype_entity_solid", true);
        registerVanillaLitFiltered("rendertype_entity_cutout", true);
        registerVanillaLitFiltered("rendertype_entity_translucent", true);
        registerVanillaLitFiltered("rendertype_entity_cutout_no_cull", true);

        ShaderPatchRegistry.register(
                "rendertype_leash",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_leash",
                new VanillaLightFragNotColorPatch()
        );

        registerVanillaLitFiltered("rendertype_entity_smooth_cutout", true);
        registerVanillaLitFiltered("rendertype_entity_translucent_cull", true);
        registerVanillaLitFiltered("rendertype_item_entity_translucent_cull", true);
        registerVanillaLitFiltered("rendertype_entity_cutout_no_cull_z_offset", true);
        registerVanillaLitFiltered("rendertype_cutout_mipped", true);
        registerVanillaLitFiltered("rendertype_solid", true);
        registerVanillaLitFiltered("particle", true);
        registerVanillaLitFiltered("rendertype_cutout", true);
        registerVanillaLitFiltered("forge:rendertype_entity_unlit_translucent", true);
        registerVanillaLitFiltered("position_tex_color_normal", true);
        registerVanillaLitFiltered("rendertype_armor_cutout_no_cull", true);
        registerVanillaLitFiltered("rendertype_entity_decal", true);
        registerVanillaLitFiltered("rendertype_entity_no_outline", true);
        registerVanillaLitFiltered("rendertype_entity_shadow", true);
        registerVanillaLitFiltered("rendertype_entity_translucent_emissive", true);
        registerVanillaLitFiltered("rendertype_outline", true);
        registerVanillaLitFiltered("rendertype_translucent", true);
        registerVanillaLitFiltered("rendertype_tripwire", true);
    }

    private static void registerVanillaLitFiltered(String shaderName, boolean translucent) {
        ShaderPatchRegistry.register(
                shaderName,
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                shaderName,
                new VanillaLightFragPatch()
        );
        if (translucent) {
            ShaderPatchRegistry.register(
                    shaderName,
                    new VanillaFilterFragPatch()
            );
        }
    }

    public static void register(String shaderName, ShaderPatch patch) {
        PATCHES.computeIfAbsent(shaderName, k -> new ArrayList<>()).add(patch);
    }

    public static String apply(String name, String source, ShaderContext ctx) {
        var patches = PATCHES.get(name);
        if (patches != null) {
            for (var patch : patches) {
                source = patch.apply(source, ctx);
            }
            ShaderedMod.LOGGER.info("Applied shader patch to: " + name);
        }
        return source;
    }
}