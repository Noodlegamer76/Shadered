package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.IllusoriteOreBlockEntity;
import com.noodlegamer76.shadered.entity.block.InitBlockEntities;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.item.SkyblockHolderItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class IllusoriteOreBlock extends SkyblockHolderBlock implements EntityBlock {
    public IllusoriteOreBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IllusoriteOreBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.emptyList();
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof SkyblockHolderEntity entity) {
                SkyblockType type = entity.getBlockType();
                SkyblockPass pass = entity.getPass();

                ItemStack stack;
                if (player.getMainHandItem().getAllEnchantments().containsKey(Enchantments.SILK_TOUCH)) {
                    stack = new ItemStack(this);

                    CompoundTag tag = new CompoundTag();
                    tag.putString("blockType", entity.getBlockType().name());
                    tag.putString("pass", entity.getPass().name());

                    stack.getOrCreateTag().put("BlockEntityTag", tag);
                } else {
                    Item itemType = SkyblockRegistry.getItem(type).getItem();
                    stack = SkyblockHolderItem.create(type, pass, itemType);
                    stack.setCount(level.random.nextInt(8, 16));
                }

                if (!player.isCreative()) {
                    popResource(level, pos, stack);
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level,
                            BlockPos pos,
                            BlockState state,
                            LivingEntity placer,
                            ItemStack stack) {

        super.setPlacedBy(level, pos, state, placer, stack);

        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();

            if (tag.contains("BlockEntityTag")) {
                CompoundTag beTag = tag.getCompound("BlockEntityTag");

                BlockEntity be = level.getBlockEntity(pos);

                if (be instanceof IllusoriteOreBlockEntity oreEntity) {
                    try {
                        oreEntity.setBlockType(
                                SkyblockType.valueOf(beTag.getString("blockType"))
                        );
                    } catch (IllegalArgumentException ignored) {}

                    try {
                        oreEntity.setPass(
                                SkyblockPass.valueOf(beTag.getString("pass"))
                        );
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        }
    }
}
