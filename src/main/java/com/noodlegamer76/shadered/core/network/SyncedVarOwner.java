package com.noodlegamer76.shadered.core.network;

import java.util.List;

public interface SyncedVarOwner {
    void markDirty(SyncedVar<?> var);

    List<SyncedVar<?>> getSyncedData();
}

