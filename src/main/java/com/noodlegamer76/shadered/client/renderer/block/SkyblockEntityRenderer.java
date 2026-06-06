package com.noodlegamer76.shadered.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.shadered.client.renderer.SkyblockRenderer;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SkyblockEntityRenderer<T extends SkyblockHolderEntity> implements BlockEntityRenderer<T> {

    public SkyblockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        SkyblockPass pass = pBlockEntity.getPass();
        SkyblockType type = pBlockEntity.getBlockType();

        switch (type) {
            case SPACE: {
                SkyblockRenderer.spaceData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case END: {
                SkyblockRenderer.endData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case ECLIPSE: {
                SkyblockRenderer.eclipseData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case IRIDIA: {
                SkyblockRenderer.iridiaData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case LIGHT: {
                SkyblockRenderer.lightData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case MIMIC: {
                SkyblockRenderer.mimicData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case OCEAN: {
                SkyblockRenderer.oceanData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case FOREST: {
                SkyblockRenderer.forestData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case STORMY: {
                SkyblockRenderer.stormyData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
            case END_SKY: {
                SkyblockRenderer.endSkyData.add(pass, pBlockEntity.getBlockPos(), pPoseStack.last().pose(), false, 1.0F);
                break;
            }
        }
    }
}
