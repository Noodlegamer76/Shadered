package com.noodlegamer76.shadered.item;

import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class SkyblockFilter extends Item {
    private final SkyblockPass pass;

    public SkyblockFilter(Properties pProperties, SkyblockPass pass) {
        super(pProperties);
        this.pass = pass;
    }

    public SkyblockPass getPass() {
        return pass;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (!ctx.getLevel().isClientSide && ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof SkyblockHolderEntity entity) {
            entity.setPass(pass);
            return InteractionResult.SUCCESS;
        }
        if (ctx.getLevel().isClientSide && ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof SkyblockHolderEntity) {
            return InteractionResult.SUCCESS;
        }
        return super.useOn(ctx);
    }
}
