package com.noodlegamer76.shadered.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.noodlegamer76.shadered.client.util.SkyBlockRenderInfo;

import java.util.ArrayList;

public class RenderTargets {
    public static RenderTarget SPACE = new TextureTarget(1, 1, false, false);
    public static RenderTarget OCEAN = new TextureTarget(1, 1, false, false);
    public static RenderTarget STORMY = new TextureTarget(1, 1, false, false);
    public static RenderTarget END_SKY = new TextureTarget(1, 1, false, false);

    public static final ArrayList<SkyBlockRenderInfo> spaceRenderInfos = new ArrayList<>();
    public static final ArrayList<SkyBlockRenderInfo> oceanRenderInfos = new ArrayList<>();
    public static final ArrayList<SkyBlockRenderInfo> stormyRenderInfos = new ArrayList<>();
    public static final ArrayList<SkyBlockRenderInfo> endRenderInfos = new ArrayList<>();
    public static final ArrayList<SkyBlockRenderInfo> endSkyRenderInfos = new ArrayList<>();
}
