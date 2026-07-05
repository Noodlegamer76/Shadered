package com.noodlegamer76.shadered.client.util.shader;

import com.noodlegamer76.shadered.ShaderedMod;
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
                "blocks/block_layer_opaque.vsh",
                new InjectWorldPosVertexPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_solid",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_solid",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_cutout",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_cutout",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_translucent",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_translucent",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_cutout_no_cull",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_cutout_no_cull",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_leash",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_leash",
                new VanillaLightFragNotColorPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_smooth_cutout",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_smooth_cutout",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_translucent_cull",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_translucent_cull",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_item_entity_translucent_cull",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_item_entity_translucent_cull",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_cutout_no_cull_z_offset",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_cutout_no_cull_z_offset",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_cutout_mipped",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_cutout_mipped",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_solid",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_solid",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "particle",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "particle",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_cutout",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_cutout",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "forge:rendertype_entity_unlit_translucent",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "forge:rendertype_entity_unlit_translucent",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "position_tex_color_normal",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "position_tex_color_normal",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_armor_cutout_no_cull",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_armor_cutout_no_cull",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_decal",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_decal",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_no_outline",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_no_outline",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_shadow",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_shadow",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_entity_translucent_emissive",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_entity_translucent_emissive",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_outline",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_outline",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_translucent",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_translucent",
                new VanillaLightFragPatch()
        );

        ShaderPatchRegistry.register(
                "rendertype_tripwire",
                new VanillaLightVertexPatch()
        );
        ShaderPatchRegistry.register(
                "rendertype_tripwire",
                new VanillaLightFragPatch()
        );
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