package com.noodlegamer76.shadered.client.util.glass;

import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class GlassChannel {
    private final BlockPos portalPos;

    public GlassChannel(BlockPos portalPos) {
        this.portalPos = portalPos;
    }

    public BlockPos getPortalPos() {
        return portalPos;
    }

    public Vec3 getOffset(Camera camera) {
        return camera.getPosition().add(portalPos.getX(), portalPos.getY(), portalPos.getZ());
    }
}
