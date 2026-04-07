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

    public void add(SkyblockPass pass, BlockPos pos, Matrix4f pose, boolean invert, float alpha) {
        passes.computeIfAbsent(pass, k -> new PassData());
        passes.get(pass).getPositions().add(pos);
        passes.get(pass).getPose().add(pose);
        passes.get(pass).getAlphas().add(alpha);
        passes.get(pass).getInverts().add(invert);
        if (invert) {
            passes.get(pass).getInvertedIndices().add(passes.get(pass).getPositions().size() - 1);
        }
    }

    public PassData get(SkyblockPass pass) {
        return passes.get(pass);
    }

    public static class PassData {
        private final List<BlockPos> positions = new ArrayList<>();
        private final List<Matrix4f> pose = new ArrayList<>();
        private final List<Float> alphas = new ArrayList<>();
        private final List<Boolean> inverts = new ArrayList<>();
        private final List<Integer> invertedIndices = new ArrayList<>();

        public List<BlockPos> getPositions() {
            return positions;
        }

        public List<Matrix4f> getPose() {
            return pose;
        }

        public List<Float> getAlphas() {
            return alphas;
        }

        public List<Boolean> getInverts() {
            return inverts;
        }

        public List<Integer> getInvertedIndices() {
            return invertedIndices;
        }
    }
}
