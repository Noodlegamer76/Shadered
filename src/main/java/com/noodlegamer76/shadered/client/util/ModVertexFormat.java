package com.noodlegamer76.shadered.client.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class ModVertexFormat {
    public static final VertexFormatElement ELEMENT_TANGENT =
            new VertexFormatElement(
                    0,
                    4,
                    VertexFormatElement.Type.FLOAT,
                    VertexFormatElement.Usage.UV,
                    4
            );
    public static final VertexFormatElement ELEMENT_INDICES =
            new VertexFormatElement(
                    0,
                    5,
                    VertexFormatElement.Type.FLOAT,
                    VertexFormatElement.Usage.UV,
                    4
            );
    public static final VertexFormatElement ELEMENT_WEIGHTS =
            new VertexFormatElement(
                    0,
                    6,
                    VertexFormatElement.Type.FLOAT,
                    VertexFormatElement.Usage.UV,
                    4
            );

    public static final VertexFormat PBR = VertexFormat.builder()
            .add("Position", VertexFormatElement.POSITION)
            .add("UV0", VertexFormatElement.UV0)
            .add("Color", VertexFormatElement.COLOR)
            .add("Normal", VertexFormatElement.NORMAL)
            .add("Tangent", ELEMENT_TANGENT)
            .add("BoneIDs", ELEMENT_INDICES)
            .add("BoneWeights", ELEMENT_WEIGHTS)
            .build();

}
