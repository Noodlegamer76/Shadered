package com.noodlegamer76.shadered.client.util;

import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

public class SkyBlockRenderInfo {
    public final BlockPos pos;
    public final Matrix4f pose;

    public SkyBlockRenderInfo(BlockPos pos, Matrix4f pose) {
        this.pos = pos;
        this.pose = pose;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Matrix4f getPose() {
        return pose;
    }
}
