package com.noodlegamer76.shadered.client.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class ModVertexFormat {
    public static final VertexFormatElement ELEMENT_TANGENT =
            new VertexFormatElement(
                    0,
                    VertexFormatElement.Type.FLOAT,
                    VertexFormatElement.Usage.UV,
                    4
            );
    public static final VertexFormatElement ELEMENT_INDICES =
            new VertexFormatElement(
                    0,
                    VertexFormatElement.Type.FLOAT,
                    VertexFormatElement.Usage.UV,
                    4
            );
    public static final VertexFormatElement ELEMENT_WEIGHTS =
            new VertexFormatElement(
                    0,
                    VertexFormatElement.Type.FLOAT,
                    VertexFormatElement.Usage.UV,
                    4
            );

    public static final VertexFormat PBR = new VertexFormat(
            ImmutableMap.<String, VertexFormatElement>builder()
                    .put("Position", ELEMENT_POSITION)
                    .put("UV0", ELEMENT_UV0)
                    .put("Color", ELEMENT_COLOR)
                    .put("Normal", ELEMENT_NORMAL)
                    .put("Tangent", ELEMENT_TANGENT)
                    .put("BoneIDs", ELEMENT_INDICES)
                    .put("BoneWeights", ELEMENT_WEIGHTS)
                    .build());

}
