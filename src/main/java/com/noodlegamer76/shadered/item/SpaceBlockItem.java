package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.renderer.bewlr.SpaceBlockItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SpaceBlockItem extends BlockItem {
    public SpaceBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    //@Override
    //public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    //    consumer.accept(new IClientItemExtensions() {
    //        private SpaceBlockItemRenderer renderer = null;
    //        @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
    //            if (this.renderer == null)
    //                this.renderer = new SpaceBlockItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    //            return renderer;
    //        }
    //    });
    //}
}
