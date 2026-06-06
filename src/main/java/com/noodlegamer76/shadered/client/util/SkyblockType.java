package com.noodlegamer76.shadered.client.util;

import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockBatchData;
import net.minecraft.network.chat.Component;

public enum SkyblockType {
    SPACE("skyblock_type.shadered.space", SkyblockRenderer.spaceData),
    ECLIPSE("skyblock_type.shadered.eclipse", SkyblockRenderer.eclipseData),
    END("skyblock_type.shadered.end", SkyblockRenderer.endData),
    FOREST("skyblock_type.shadered.forest", SkyblockRenderer.forestData),
    STORMY("skyblock_type.shadered.stormy", SkyblockRenderer.stormyData),
    LIGHT("skyblock_type.shadered.light", SkyblockRenderer.lightData),
    MIMIC("skyblock_type.shadered.mimic", SkyblockRenderer.mimicData),
    OCEAN("skyblock_type.shadered.ocean", SkyblockRenderer.oceanData),
    IRIDIA("skyblock_type.shadered.iridia", SkyblockRenderer.iridiaData),
    END_SKY("skyblock_type.shadered.end_sky", SkyblockRenderer.endSkyData);

    private final String translationKey;
    private final SkyblockBatchData data;

    SkyblockType(String translationKey, SkyblockBatchData data) {
        this.translationKey = translationKey;
        this.data = data;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }

    public SkyblockBatchData getData() {
        return data;
    }
}