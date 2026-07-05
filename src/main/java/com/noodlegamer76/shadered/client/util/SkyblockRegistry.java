package com.noodlegamer76.shadered.client.util;

import com.noodlegamer76.shadered.item.SkyblockItemTypes;

import java.util.HashMap;
import java.util.Map;

public class SkyblockRegistry {

    private static final Map<SkyblockType, SkyblockItemTypes> TYPE_TO_ITEM = new HashMap<>();
    private static final Map<SkyblockItemTypes, SkyblockType> ITEM_TO_TYPE = new HashMap<>();

    static {
        TYPE_TO_ITEM.put(SkyblockType.SPACE, SkyblockItemTypes.SPACE);
        TYPE_TO_ITEM.put(SkyblockType.STORMY, SkyblockItemTypes.STORMY);
        TYPE_TO_ITEM.put(SkyblockType.OCEAN, SkyblockItemTypes.OCEAN);
        TYPE_TO_ITEM.put(SkyblockType.LIGHT, SkyblockItemTypes.LIGHT);
        TYPE_TO_ITEM.put(SkyblockType.END, SkyblockItemTypes.END);
        TYPE_TO_ITEM.put(SkyblockType.IRIDIA, SkyblockItemTypes.IRIDIA);
        TYPE_TO_ITEM.put(SkyblockType.END_SKY, SkyblockItemTypes.END_SKY);
        TYPE_TO_ITEM.put(SkyblockType.ECLIPSE, SkyblockItemTypes.ECLIPSE);
        TYPE_TO_ITEM.put(SkyblockType.FOREST, SkyblockItemTypes.FOREST);
        TYPE_TO_ITEM.put(SkyblockType.MIMIC, SkyblockItemTypes.MIMIC);

        for (var entry : TYPE_TO_ITEM.entrySet()) {
            ITEM_TO_TYPE.put(entry.getValue(), entry.getKey());
        }
    }

    public static SkyblockItemTypes getItem(SkyblockType type) {
        return TYPE_TO_ITEM.get(type);
    }

    public static SkyblockType getType(SkyblockItemTypes itemType) {
        return ITEM_TO_TYPE.get(itemType);
    }
}