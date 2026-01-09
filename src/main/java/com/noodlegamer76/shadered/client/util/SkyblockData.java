package com.noodlegamer76.shadered.client.util;

import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class SkyblockData {
    private final List<BlockPos> pos = new ArrayList<>();
    private final List<Matrix4f> matrices = new ArrayList<>();

    public List<BlockPos> getPos() {
        return pos;
    }

    public List<Matrix4f> getMatrices() {
        return matrices;
    }

    public void add(BlockPos pos, Matrix4f matrix) {
        this.pos.add(pos);
        this.matrices.add(matrix);
    }

    public void clear() {
        pos.clear();
        matrices.clear();
    }
}
