package com.noodlegamer76.shadered.client.util.skyblock;

import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyblockBatchData {
    private final Map<SkyblockPass, PassData> passes = new HashMap<>();

    public void clear() {
        passes.clear();
    }

    public void add(SkyblockPass pass, BlockPos pos, Matrix4f pose) {
        passes.computeIfAbsent(pass, k -> new PassData());
        passes.get(pass).getPositions().add(pos);
        passes.get(pass).getPose().add(pose);
    }

    public PassData get(SkyblockPass pass) {
        return passes.get(pass);
    }

    public static class PassData {
        private final List<BlockPos> positions = new ArrayList<>();
        private final List<Matrix4f> pose = new ArrayList<>();

        public List<BlockPos> getPositions() {
            return positions;
        }

        public List<Matrix4f> getPose() {
            return pose;
        }
    }
}
