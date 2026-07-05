package com.noodlegamer76.shadered.client.util;

import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import com.noodlegamer76.shadered.item.SkyblockItemTypes;
import net.minecraft.network.chat.Component;

public enum SkyblockType {
    SPACE("skyblock_type.shadered.space"),
    ECLIPSE("skyblock_type.shadered.eclipse"),
    END("skyblock_type.shadered.end"),
    FOREST("skyblock_type.shadered.forest"),
    STORMY("skyblock_type.shadered.stormy"),
    LIGHT("skyblock_type.shadered.light"),
    MIMIC("skyblock_type.shadered.mimic"),
    OCEAN("skyblock_type.shadered.ocean"),
    IRIDIA("skyblock_type.shadered.iridia"),
    END_SKY("skyblock_type.shadered.end_sky");

    private final String translationKey;

    SkyblockType(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }
}