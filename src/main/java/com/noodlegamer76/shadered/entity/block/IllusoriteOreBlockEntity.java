package com.noodlegamer76.shadered.entity.block;

import com.noodlegamer76.shadered.client.util.SkyblockType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class IllusoriteOreBlockEntity extends SkyblockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private SkyblockType blockType = SkyblockType.STORMY;

    public IllusoriteOreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.ILLUSORITE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("blockType", blockType.name());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("blockType")) {
            blockType = SkyblockType.valueOf(tag.getString("blockType"));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putString("blockType", blockType.name());
        return tag;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    public void setBlockType(SkyblockType blockType) {
        this.blockType = blockType;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public SkyblockType getBlockType() {
        return blockType;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
