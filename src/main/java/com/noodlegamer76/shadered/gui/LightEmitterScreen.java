package com.noodlegamer76.shadered.gui;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import com.noodlegamer76.shadered.network.lightemitterpayload.LightEmitterPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import net.neoforged.neoforge.network.PacketDistributor;

public class LightEmitterScreen extends Screen {
    ExtendedSlider redSlider;
    ExtendedSlider blueSlider;
    ExtendedSlider greenSlider;
    ExtendedSlider radiusSlider;
    private final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(ShaderedMod.MODID, "textures/screens/basic_background.png");
    private final LightBulbEntity entity;

    public LightEmitterScreen(LightBulbEntity entity) {
        super(Component.translatable("gui.shadered.light_emitter.title"));
        this.entity = entity;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int cornerX = centerX - 128;
        int cornerY = centerY - 128;

        redSlider = new ExtendedSlider(
                cornerX + 10,
                cornerY + 10,
                236, 20,
                Component.translatable("gui.shadered.light_emitter.red"),
                Component.empty(),
                0, 255,
                entity.getRed() * 255,
                1, 1,
                true
        );
        addRenderableWidget(redSlider);

        greenSlider = new ExtendedSlider(
                cornerX + 10,
                cornerY + 40,
                236, 20,
                Component.translatable("gui.shadered.light_emitter.green"),
                Component.empty(),
                0, 255,
                entity.getGreen() * 255,
                1, 1,
                true
        );
        addRenderableWidget(greenSlider);

        blueSlider = new ExtendedSlider(
                cornerX + 10,
                cornerY + 70,
                236, 20,
                Component.translatable("gui.shadered.light_emitter.blue"),
                Component.empty(),
                0, 255,
                entity.getBlue() * 255,
                1, 1,
                true
        );
        addRenderableWidget(blueSlider);

        radiusSlider = new ExtendedSlider(
                cornerX + 10,
                cornerY + 100,
                236, 20,
                Component.translatable("gui.shadered.light_emitter.radius"),
                Component.empty(),
                0, 100,
                entity.getRadius(),
                1, 1,
                true
        );
        addRenderableWidget(radiusSlider);


    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

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
        LightEmitterPayload packet = new LightEmitterPayload(
                entity.getBlockPos(),
                (float) redSlider.getValue() / 255,
                (float) greenSlider.getValue() / 255,
                (float) blueSlider.getValue() / 255,
                (float) radiusSlider.getValue()
        );
        PacketDistributor.sendToServer(packet);
    }
}
