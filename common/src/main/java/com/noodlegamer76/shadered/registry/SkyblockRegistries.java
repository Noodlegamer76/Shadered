package com.noodlegamer76.shadered.registry;

import java.util.ArrayList;

public class SkyblockRegistries {
    private static final ArrayList<SkyBlockRegistry> registries = new ArrayList<>();

    public static ArrayList<SkyBlockRegistry> getRegistries() {
        return registries;
    }

    public static void addRegistry(SkyBlockRegistry registry) {
        registries.add(registry);
    }

    static {
        addRegistry(new SkyBlockRegistry("space", ));
        addRegistry(new SkyBlockRegistry("ocean", ));
    }
}
