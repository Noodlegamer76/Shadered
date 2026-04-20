package com.noodlegamer76.shadered.gui;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.entity.block.LightBulbEntity;
import com.noodlegamer76.shadered.entity.block.SkyEmitterEntity;
import com.noodlegamer76.shadered.network.LightEmitterPacket;
import com.noodlegamer76.shadered.network.PacketHandler;
import com.noodlegamer76.shadered.network.SkyEmitterPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class LightEmitterScreen extends Screen {
    ForgeSlider redSlider;
    ForgeSlider blueSlider;
    ForgeSlider greenSlider;
    ForgeSlider radiusSlider;
    private final ResourceLocation BACKGROUND = new ResourceLocation(ShaderedMod.MODID, "textures/screens/basic_background.png");
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

        redSlider = new ForgeSlider(
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

        greenSlider = new ForgeSlider(
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

        blueSlider = new ForgeSlider(
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

        radiusSlider = new ForgeSlider(
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
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        pGuiGraphics.blit(
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
        LightEmitterPacket packet = new LightEmitterPacket(
                entity.getBlockPos(),
                (float) redSlider.getValue() / 255,
                (float) greenSlider.getValue() / 255,
                (float) blueSlider.getValue() / 255,
                (float) radiusSlider.getValue()
        );
        PacketHandler.sendToServer(packet);
    }
}
