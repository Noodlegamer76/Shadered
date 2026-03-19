package com.noodlegamer76.shadered.client.util;

import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class SkyblockBatchData {
    private final List<BlockPos> positions = new ArrayList<>();
    private final List<Matrix4f> pose = new ArrayList<>();

    public List<BlockPos> getPositions() {
        return positions;
    }

    public List<Matrix4f> getPose() {
        return pose;
    }

    public void clear() {
        positions.clear();
        pose.clear();
    }

    public void add(BlockPos pos, Matrix4f pose) {
        positions.add(pos);
        this.pose.add(pose);
    }
}
