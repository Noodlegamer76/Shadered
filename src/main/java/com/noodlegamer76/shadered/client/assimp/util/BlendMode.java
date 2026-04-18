package com.noodlegamer76.shadered.client.assimp.util;

public enum BlendMode {
    OPAQUE, // No blending
    ALPHA, // Standard transparency (SRC_ALPHA, ONE_MINUS_SRC_ALPHA)
    ADDITIVE, // Glowing effects (SRC_ALPHA, ONE)
    MULTIPLY // Shadow effects (DST_COLOR, ZERO)
}