package com.noodlegamer76.shadered.gui;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import com.noodlegamer76.shadered.network.skyemitter.SkyEmitterPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import net.neoforged.neoforge.network.PacketDistributor;

public class SkyEmitterScreen extends Screen {
    ExtendedSlider minDistance;
    ExtendedSlider maxDistance;
    ExtendedSlider maxAlpha;
    private final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/screens/basic_background.png");
    private final SkyEmitterEntity entity;

    public SkyEmitterScreen(SkyEmitterEntity entity) {
        super(Component.translatable("gui.shadered.sky_emitter.title"));
        this.entity = entity;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int cornerX = centerX - 128;
        int cornerY = centerY - 128;

        minDistance = new ExtendedSlider(
                cornerX + 10,
                cornerY + 10,
                236, 20,
                Component.translatable("gui.shadered.sky_emitter.min_distance"),
                Component.empty(),
                0, 256,
                entity.getMinimumRange(),
                1, 1,
                true
        );
        addRenderableWidget(minDistance);

        maxDistance = new ExtendedSlider(
                cornerX + 10,
                cornerY + 40,
                236, 20,
                Component.translatable("gui.shadered.sky_emitter.max_distance"),
                Component.empty(),
                0, 256,
                entity.getMaximumRange(),
                1, 1,
                true
        );
        addRenderableWidget(maxDistance);

        maxAlpha = new ExtendedSlider(
                cornerX + 10,
                cornerY + 70,
                236, 20,
                Component.translatable("gui.shadered.sky_emitter.max_alpha"),
                Component.empty(),
                0, 255,
                entity.getAlpha() * 255,
                1, 1,
                true
        );
        addRenderableWidget(maxAlpha);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderMenuBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderMenuBackground(GuiGraphics guiGraphics) {
        super.renderMenuBackground(guiGraphics);
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        guiGraphics.blit(
                BACKGROUND,
                centerX - 128,
                centerY - 128,
                256, 256,
                256 ,256
        );

    }

    @Override
    public void onClose() {
        super.onClose();
        SkyEmitterPacket packet = new SkyEmitterPacket(
                entity.getBlockPos(),
                (float) minDistance.getValue(),
                (float) maxDistance.getValue(),
                (float) maxAlpha.getValue() / 255
        );
        PacketDistributor.sendToServer(packet);
    }
}
