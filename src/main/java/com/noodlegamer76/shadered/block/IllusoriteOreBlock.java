package com.noodlegamer76.shadered.block;

import com.noodlegamer76.shadered.client.util.SkyblockRegistry;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;
import com.noodlegamer76.shadered.entity.block.IllusoriteOreBlockEntity;
import com.noodlegamer76.shadered.entity.block.SkyblockHolderEntity;
import com.noodlegamer76.shadered.core.component.InitDataComponents;
import com.noodlegamer76.shadered.item.SkyblockHolderBlockItem;
import com.noodlegamer76.shadered.item.SkyblockData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
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
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof SkyblockHolderEntity entity) {
                SkyblockType type = entity.getBlockType();
                SkyblockPass pass = entity.getPass();

                ItemStack stack;
                var enchantmentLookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                var silkTouchHolder = enchantmentLookup.getOrThrow(Enchantments.SILK_TOUCH);

                if (player.getMainHandItem().getEnchantmentLevel(silkTouchHolder) > 0) {
                    stack = new ItemStack(this);

                    SkyblockType itemTypeVal = entity.getBlockType() != null ? entity.getBlockType() : SkyblockType.STORMY;
                    SkyblockPass itemPassVal = entity.getPass() != null ? entity.getPass() : SkyblockPass.NORMAL;

                    stack.set(InitDataComponents.SKYBLOCK_DATA.get(), new SkyblockData(itemTypeVal, itemPassVal));
                } else {
                    Item itemType = SkyblockRegistry.getItem(type).getItem();
                    stack = SkyblockHolderBlockItem.create(type, pass, itemType);
                    stack.setCount(level.random.nextInt(8, 16));
                }

                if (!player.isCreative()) {
                    popResource(level, pos, stack);
                }
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level,
                            BlockPos pos,
                            BlockState state,
                            LivingEntity placer,
                            ItemStack stack) {

        super.setPlacedBy(level, pos, state, placer, stack);

        SkyblockData data = stack.get(InitDataComponents.SKYBLOCK_DATA.get());
        if (data != null) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof IllusoriteOreBlockEntity oreEntity) {
                oreEntity.setBlockType(data.type() != null ? data.type() : SkyblockType.STORMY);
                oreEntity.setPass(data.pass() != null ? data.pass() : SkyblockPass.NORMAL);
            }
        }
    }
}